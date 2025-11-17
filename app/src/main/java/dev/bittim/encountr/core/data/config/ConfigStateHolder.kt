/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ConfigStateHolder.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 02:31
 */

package dev.bittim.encountr.core.data.config

import kotlinx.coroutines.flow.StateFlow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface ConfigStateHolder {
    val rawState: StateFlow<ConfigState?>
    val state: StateFlow<ResolvedConfigState?>

    suspend fun init()
    suspend fun reset()

    suspend fun setOnboardingCompleted(completed: Boolean)
    suspend fun setLanguageId(id: Int)
    suspend fun setCurrentSaveUuid(uuid: Uuid)
}