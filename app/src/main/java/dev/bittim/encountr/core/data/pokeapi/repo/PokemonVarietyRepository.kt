/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonVarietyRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.09.25, 18:03
 */

package dev.bittim.encountr.core.data.pokeapi.repo

import co.pokeapi.pokekotlin.model.PokemonVariety

interface PokemonVarietyRepository {
    suspend fun get(id: Int): PokemonVariety?
}