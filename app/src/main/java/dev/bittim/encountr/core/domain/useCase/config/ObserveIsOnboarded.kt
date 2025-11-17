/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ObserveIsOnboarded.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 02:31
 */

package dev.bittim.encountr.core.domain.useCase.config

import dev.bittim.encountr.core.data.config.ConfigStateHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class ObserveIsOnboarded(
    private val configStateHolder: ConfigStateHolder
) {
    operator fun invoke(): Flow<Boolean?> {
        return configStateHolder.rawState.map { configState ->
            configState?.onboardingCompleted
        }.distinctUntilChanged().flowOn(Dispatchers.IO)
    }
}