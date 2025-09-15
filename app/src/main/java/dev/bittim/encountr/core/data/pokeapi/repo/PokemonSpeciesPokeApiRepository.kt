/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonSpeciesPokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.09.25, 22:35
 */

package dev.bittim.encountr.core.data.pokeapi.repo

import co.pokeapi.pokekotlin.PokeApi
import co.pokeapi.pokekotlin.model.PokemonSpecies

class PokemonSpeciesPokeApiRepository : PokemonSpeciesRepository {
    override suspend fun get(id: Int): PokemonSpecies? {
        return PokeApi.getPokemonSpecies(id)
    }
}