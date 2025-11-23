/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionGroupRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   23.11.25, 17:43
 */

package dev.bittim.encountr.core.data.api.repo.versionGroup

import androidx.work.WorkManager
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.repo.Repository
import dev.bittim.encountr.core.domain.model.api.versionGroup.VersionGroup
import kotlinx.coroutines.flow.Flow

abstract class VersionGroupRepository(
    workManager: WorkManager,
    apiDatabase: ApiDatabase
) : Repository<VersionGroup>(workManager, apiDatabase) {
    // region:      -- Type

    override val type: String = VersionGroup::class.java.simpleName

    // endregion:   -- Type
    // region:      -- Additional functions

    abstract fun getVersionIds(id: Int): Flow<List<Int>>
    abstract fun getPokedexIds(id: Int): Flow<List<Int>>

    // endregion:   -- Additional functions
}