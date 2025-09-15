/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionPokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.09.25, 18:03
 */

package dev.bittim.encountr.core.data.pokeapi.repo

import android.util.Log
import co.pokeapi.pokekotlin.PokeApi
import co.pokeapi.pokekotlin.model.Generation
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepository
import dev.bittim.encountr.core.data.pokeapi.mapping.mapPokemonSpriteVersion
import dev.bittim.encountr.core.domain.model.pokeapi.LocalizedString
import dev.bittim.encountr.core.domain.model.pokeapi.Version
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class VersionPokeApiRepository(
    private val definitionRepository: DefinitionRepository,
    private val pokemonVarietyRepository: PokemonVarietyRepository
) : VersionRepository {
    override suspend fun get(id: Int, providedRawGeneration: Generation?): Version? {
        val rawVersion = PokeApi.getVersion(id)
        Log.d("VersionPokeApiRepository", "Fetched Version: $rawVersion")

        var rawGeneration = providedRawGeneration
        if (rawGeneration == null) {
            Log.d("VersionPokeApiRepository", "No provided raw generation, fetching from API")
            val rawVersionGroup = PokeApi.getVersionGroup(rawVersion.versionGroup.id)
            Log.d("VersionPokeApiRepository", "Fetched Version Group: $rawVersionGroup")
            rawGeneration = PokeApi.getGeneration(rawVersionGroup.generation.id)
            Log.d("VersionPokeApiRepository", "Fetched Generation: $rawGeneration")
        }

        val iconDefinition = definitionRepository.getIconByVersion(rawVersion.id)
        Log.d("VersionPokeApiRepository", "Mapped Icon Definition: $iconDefinition")
        val imageUrl = iconDefinition?.let {
            val rawSprites = pokemonVarietyRepository.get(it.pokemon)?.sprites ?: return null
            mapPokemonSpriteVersion(rawSprites, rawVersion).frontDefault
        }
        Log.d("VersionPokeApiRepository", "Mapped Icon URL: $imageUrl")

        val version = Version(
            id = rawVersion.id,
            name = rawVersion.name,
            localizedNames = rawVersion.names.map {
                LocalizedString(it.language.name, it.name)
            },
            generationName = rawGeneration.name,
            localizedGenerationNames = rawGeneration.names.map {
                LocalizedString(it.language.name, it.name)
            },
            versionGroupId = rawVersion.versionGroup.id,
            imageUrl = imageUrl,
        )

        Log.d("VersionPokeApiRepository", "Mapped Version: $version")
        return version
    }

    override suspend fun getByGeneration(generationId: Int): List<Version> {
        val rawGeneration = PokeApi.getGeneration(generationId)
        Log.d("VersionPokeApiRepository", "Fetched Generation: $rawGeneration")

        val versions = coroutineScope {
            rawGeneration.versionGroups.map { versionGroupHandle ->
                async(Dispatchers.IO) {
                    val rawVersionGroup = PokeApi.getVersionGroup(versionGroupHandle.id)
                    rawVersionGroup.versions.map { versionHandle ->
                        async(Dispatchers.IO) {
                            get(id = versionHandle.id, providedRawGeneration = rawGeneration)
                                .takeIf { !definitionRepository.checkIgnored(versionHandle.id) }
                        }
                    }.awaitAll().filterNotNull()
                }
            }.awaitAll().flatten()
        }

        Log.d("VersionPokeApiRepository", "Mapped ${versions.size} Versions")
        return versions
    }
}