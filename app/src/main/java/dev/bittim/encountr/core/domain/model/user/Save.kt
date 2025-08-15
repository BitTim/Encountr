/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Save.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   14.08.25, 22:28
 */

package dev.bittim.encountr.core.domain.model.user

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Save @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid,
    val name: String,
    val game: String,
)
