/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TimestampedEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.entity.base

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

interface TimestampedEntity {
    val updatedAt: Long

    companion object {
        @OptIn(ExperimentalTime::class)
        fun generateTimestamp(): Long = Clock.System.now().epochSeconds
    }
}