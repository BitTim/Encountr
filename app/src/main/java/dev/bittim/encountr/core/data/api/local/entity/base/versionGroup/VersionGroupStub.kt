/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionGroupStub.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.11.25, 03:05
 */

package dev.bittim.encountr.core.data.api.local.entity.base.versionGroup

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.data.api.local.entity.base.StubEntity
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationStub

@Entity(
    tableName = "version_group_stub",
    foreignKeys = [
        ForeignKey(
            entity = GenerationStub::class,
            parentColumns = ["id"],
            childColumns = ["generationId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class VersionGroupStub(
    @PrimaryKey override val id: Int,
    val generationId: Int?,
    override val updatedAt: Long = StubEntity.generateTimestamp()
) : StubEntity
