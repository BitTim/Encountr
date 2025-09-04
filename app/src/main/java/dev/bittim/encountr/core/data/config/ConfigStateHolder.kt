/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ConfigStateHolder.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   04.09.25, 23:26
 */

package dev.bittim.encountr.core.data.config

import kotlinx.coroutines.flow.StateFlow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface ConfigStateHolder {
    val configState: StateFlow<ConfigState>

    suspend fun init()
    suspend fun reset()

    suspend fun setDefinitionsUrl(url: String)
    suspend fun setLanguageName(name: String)
    suspend fun setCurrentSaveUuid(uuid: Uuid)
}