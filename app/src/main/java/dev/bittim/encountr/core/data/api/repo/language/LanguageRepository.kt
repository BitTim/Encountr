/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LanguageRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   23.11.25, 17:35
 */

package dev.bittim.encountr.core.data.api.repo.language

import androidx.work.WorkManager
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.repo.Repository
import dev.bittim.encountr.core.domain.model.api.language.Language

abstract class LanguageRepository(
    workManager: WorkManager,
    apiDatabase: ApiDatabase
) : Repository<Language?>(workManager, apiDatabase) {

    // region:      -- Type

    override val type: String = Language::class.java.simpleName

    // endregion:   -- Type
}