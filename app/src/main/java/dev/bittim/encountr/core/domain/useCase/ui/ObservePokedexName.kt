/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ObservePokedexName.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 02:31
 */

package dev.bittim.encountr.core.domain.useCase.ui

import dev.bittim.encountr.core.data.api.repo.pokedex.PokedexRepository
import dev.bittim.encountr.core.domain.useCase.util.ObserveLocalizedName
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class ObservePokedexName(
    private val observeLocalizedName: ObserveLocalizedName,
    private val pokedexRepository: PokedexRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(pokedexId: Int): Flow<String> {
        return pokedexRepository.get(pokedexId).flatMapLatest { pokedex ->
            observeLocalizedName(pokedex?.localizedNames, pokedex?.name)
        }
    }
}