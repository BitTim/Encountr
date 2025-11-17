/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 20:00
 */

package dev.bittim.encountr.core.data.api.repo.pokedex

import dev.bittim.encountr.core.data.api.repo.Repository
import dev.bittim.encountr.core.domain.model.api.pokedex.Pokedex
import kotlinx.coroutines.flow.Flow

interface PokedexRepository : Repository<Pokedex> {
    fun getPokemonIds(id: Int): Flow<List<Int>>
}