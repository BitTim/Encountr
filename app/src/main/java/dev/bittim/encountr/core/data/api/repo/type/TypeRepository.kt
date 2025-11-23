/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TypeRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   23.11.25, 17:40
 */

package dev.bittim.encountr.core.data.api.repo.type

import androidx.work.WorkManager
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.repo.Repository
import dev.bittim.encountr.core.domain.model.api.type.Type

abstract class TypeRepository(
    workManager: WorkManager,
    apiDatabase: ApiDatabase
) : Repository<Type>(workManager, apiDatabase) {
    // region:      -- Type

    override val type: String = Type::class.java.simpleName

    // endregion:   -- Type
}