/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ObservePokemonIdsByPokedex.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 20:00
 */

package dev.bittim.encountr.core.domain.useCase.api

import dev.bittim.encountr.core.data.api.repo.pokedex.PokedexRepository
import kotlinx.coroutines.flow.Flow

class ObservePokemonIdsByPokedex(
    private val pokedexRepository: PokedexRepository,
) {
    operator fun invoke(pokedexId: Int): Flow<List<Int>> {
        return pokedexRepository.getPokemonIds(pokedexId)
    }
}