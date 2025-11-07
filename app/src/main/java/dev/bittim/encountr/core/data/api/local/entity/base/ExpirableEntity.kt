/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ExpirableEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.entity.base

import dev.bittim.encountr.core.di.Constants
import java.time.OffsetDateTime

interface ExpirableEntity {
    val expiresAt: Long?

    companion object {
        fun calcExpiryTime(): Long =
            OffsetDateTime.now().plusDays(Constants.API_EXPIRATION_DAYS).toEpochSecond()
    }
}