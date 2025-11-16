/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       StubEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.11.25, 03:03
 */

package dev.bittim.encountr.core.data.api.local.entity.base

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

interface StubEntity {
    val id: Int
    val updatedAt: Long

    companion object {
        @OptIn(ExperimentalTime::class)
        fun generateTimestamp(): Long = Clock.System.now().epochSeconds
    }
}