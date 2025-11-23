/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GenerationRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   23.11.25, 17:35
 */

package dev.bittim.encountr.core.data.api.repo.generation

import androidx.work.WorkManager
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.repo.Repository
import dev.bittim.encountr.core.domain.model.api.generation.Generation
import kotlinx.coroutines.flow.Flow

abstract class GenerationRepository(
    workManager: WorkManager,
    apiDatabase: ApiDatabase
) : Repository<Generation>(workManager, apiDatabase) {
    // region:      -- Type

    override val type: String = Generation::class.java.simpleName

    // endregion:   -- Type
    // region:      -- Additional functions

    abstract fun getVersionGroupIds(id: Int): Flow<List<Int>>

    // endregion:   -- Additional functions
}