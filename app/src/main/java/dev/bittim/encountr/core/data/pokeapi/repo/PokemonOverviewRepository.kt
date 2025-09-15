/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonOverviewRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.09.25, 00:53
 */

package dev.bittim.encountr.core.data.pokeapi.repo

import dev.bittim.encountr.core.domain.model.pokeapi.PokemonOverview

interface PokemonOverviewRepository {
    suspend fun get(speciesId: Int): PokemonOverview?
    suspend fun getByPokedex(id: Int): List<PokemonOverview>
}