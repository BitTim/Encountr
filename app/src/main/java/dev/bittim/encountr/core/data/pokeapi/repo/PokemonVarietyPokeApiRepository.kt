/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonVarietyPokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.09.25, 17:01
 */

package dev.bittim.encountr.core.data.pokeapi.repo

import co.pokeapi.pokekotlin.PokeApi
import co.pokeapi.pokekotlin.model.PokemonVariety

class PokemonVarietyPokeApiRepository : PokemonVarietyRepository {
    override suspend fun get(id: Int): PokemonVariety? {
        return PokeApi.getPokemonVariety(id)
    }
}