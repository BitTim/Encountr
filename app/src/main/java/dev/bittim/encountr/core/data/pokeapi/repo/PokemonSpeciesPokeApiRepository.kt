/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonSpeciesPokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   20.09.25, 02:04
 */

package dev.bittim.encountr.core.data.pokeapi.repo

import co.pokeapi.pokekotlin.PokeApi
import co.pokeapi.pokekotlin.model.PokemonSpecies

class PokemonSpeciesPokeApiRepository(
    private val pokeApi: PokeApi
) : PokemonSpeciesRepository {
    override suspend fun get(id: Int): PokemonSpecies? {
        return pokeApi.getPokemonSpecies(id)
    }
}