/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonOverviewPokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.09.25, 00:53
 */

package dev.bittim.encountr.core.data.pokeapi.repo

import android.util.Log
import dev.bittim.encountr.core.domain.model.pokeapi.LocalizedString
import dev.bittim.encountr.core.domain.model.pokeapi.PokedexEntry
import dev.bittim.encountr.core.domain.model.pokeapi.PokemonOverview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class PokemonOverviewPokeApiRepository(
    private val pokedexRepository: PokedexRepository,
    private val pokemonSpeciesRepository: PokemonSpeciesRepository,
    private val pokemonVarietyRepository: PokemonVarietyRepository,
    private val typeRepository: TypeRepository,
) : PokemonOverviewRepository {
    override suspend fun get(speciesId: Int): PokemonOverview? {
        val species = pokemonSpeciesRepository.get(speciesId) ?: return null
        Log.d("PokemonOverviewPokeApiRepository", "Raw species: $species")
        val defaultVariety =
            pokemonVarietyRepository.get(
                species.varieties.find { it.isDefault }?.variety?.id ?: return null
            ) ?: return null
        Log.d("PokemonOverviewPokeApiRepository", "Raw default variety: $defaultVariety")

        val pokemonOverview = PokemonOverview(
            id = speciesId,
            entryNumbers = species.pokedexNumbers.map {
                PokedexEntry(
                    pokedexId = it.pokedex.id,
                    entryNumber = it.entryNumber
                )
            },
            name = species.name,
            localizedNames = species.names.map {
                LocalizedString(languageName = it.language.name, value = it.name)
            },
            height = "${defaultVariety.height.toFloat().div(10)} m",
            weight = "${defaultVariety.weight.toFloat().div(10)} kg",
            sprites = defaultVariety.sprites,
            types = defaultVariety.types.map {
                typeRepository.get(it.type.id) ?: return null
            }
        )

        Log.d("PokemonOverviewPokeApiRepository", "Pokemon Overview: $pokemonOverview")
        return pokemonOverview
    }

    override suspend fun getByPokedex(id: Int): List<PokemonOverview> {
        val pokedex = pokedexRepository.get(id) ?: return emptyList()
        Log.d("PokemonOverviewPokeApiRepository", "Raw pokedex: $pokedex")

        return coroutineScope {
            pokedex.pokemonEntries.map {
                async(Dispatchers.IO) {
                    get(it.pokemonSpecies.id)
                }
            }.awaitAll().filterNotNull()
        }
    }
}