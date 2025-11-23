/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   23.11.25, 17:37
 */

package dev.bittim.encountr.core.data.api.repo.pokemon

import androidx.work.WorkManager
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.repo.Repository
import dev.bittim.encountr.core.domain.model.api.pokemon.Pokemon

abstract class PokemonRepository(
    workManager: WorkManager,
    apiDatabase: ApiDatabase
) : Repository<Pokemon>(workManager, apiDatabase) {
    // region:      -- Type

    override val type: String = Pokemon::class.java.simpleName

    // endregion:   -- Type
}