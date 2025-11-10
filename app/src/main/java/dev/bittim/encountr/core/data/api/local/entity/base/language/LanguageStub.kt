/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LanguageStub.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.entity.base.language

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.data.api.local.entity.base.TimestampedEntity

@Entity(tableName = "language_stub")
data class LanguageStub(
    @PrimaryKey val id: Int,
    val isLocalized: Boolean,
    override val updatedAt: Long = TimestampedEntity.generateTimestamp()
) : TimestampedEntity
