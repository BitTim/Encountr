/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionRepositoryImpl.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.defs.repo

import dev.bittim.encountr.core.data.defs.file.DefinitionLoader
import dev.bittim.encountr.core.data.defs.local.DefinitionsDatabase
import dev.bittim.encountr.core.di.Constants
import dev.bittim.encountr.core.domain.model.api.pokemon.PokemonSpriteVariant

class DefinitionRepositoryImpl(
    private val db: DefinitionsDatabase,
    private val loader: DefinitionLoader
) : DefinitionRepository {
    override suspend fun loadDefinition() {
        val definition = loader.get()

        db.definitionDao().delete()
        db.definitionDao().upsert(listOf(definition.toEntity()))
        db.pokedexAdditionDao()
            .upsert(definition.pokedexAdditions.map { it.toEntity() })
        db.versionAdditionDao().upsert(definition.versionAdditions.map { it.toEntity() })
    }

    override suspend fun getDefinitionIcon(): String? {
        return db.definitionDao().get()?.imageUrl
    }

    override suspend fun getPokedexAdditions(versionGroupId: Int): List<Int>? {
        return db.pokedexAdditionDao().get(versionGroupId)?.pokedexIds
    }

    override suspend fun getVersionIcon(versionId: Int): String? {
        return db.versionAdditionDao().get(versionId)?.imageUrl
    }

    override suspend fun getVersionSpriteVariant(versionId: Int): PokemonSpriteVariant {
        return db.versionAdditionDao().get(versionId)?.pokemonSpriteVariant
            ?: Constants.DEFAULT_POKEMON_SPRITE_VARIANT
    }

    override suspend fun isVersionIgnored(versionId: Int): Boolean {
        return db.definitionDao().get()?.ignoredVersionIds?.contains(versionId) == true
    }
}