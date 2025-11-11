/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ObserveCurrentLanguageId.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.11.25, 01:44
 */

package dev.bittim.encountr.core.domain.useCase.config

import dev.bittim.encountr.core.data.config.ConfigStateHolder
import dev.bittim.encountr.core.di.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveCurrentLanguageId(
    private val configStateHolder: ConfigStateHolder
) {
    operator fun invoke(): Flow<Int> {
        return configStateHolder.rawState.map { configState ->
            configState?.languageId ?: Constants.DEFAULT_LANG_ID
        }
    }
}