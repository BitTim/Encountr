/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ConfigState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   04.09.25, 23:26
 */

package dev.bittim.encountr.core.data.config

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class ConfigState(
    val definitionsUrl: String? = null,
    val languageName: String? = null,
    val currentSaveUuid: Uuid? = null,
)
