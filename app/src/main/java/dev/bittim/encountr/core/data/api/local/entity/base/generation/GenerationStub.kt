/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GenerationStub.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.entity.base.generation

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.data.api.local.entity.base.TimestampedEntity

@Entity(tableName = "generation_stub")
data class GenerationStub(
    @PrimaryKey val id: Int,
    override val updatedAt: Long = TimestampedEntity.generateTimestamp()
) : TimestampedEntity
