/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   23.11.25, 17:36
 */

package dev.bittim.encountr.core.data.api.repo.pokedex

import androidx.work.WorkManager
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.repo.Repository
import dev.bittim.encountr.core.domain.model.api.pokedex.Pokedex
import kotlinx.coroutines.flow.Flow

abstract class PokedexRepository(
    workManager: WorkManager,
    apiDatabase: ApiDatabase
) : Repository<Pokedex>(workManager, apiDatabase) {
    // region:      -- Type

    override val type: String = Pokedex::class.java.simpleName

    // endregion:   -- Type
    // region:      -- Additional functions

    abstract fun getPokemonIds(id: Int): Flow<List<Int>>

    // endregion:   -- Additional functions
}