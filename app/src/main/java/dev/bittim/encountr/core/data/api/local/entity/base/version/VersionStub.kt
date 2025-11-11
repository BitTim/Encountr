/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionStub.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.11.25, 15:50
 */

package dev.bittim.encountr.core.data.api.local.entity.base.version

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.data.api.local.entity.base.TimestampedEntity
import dev.bittim.encountr.core.data.api.local.entity.base.versionGroup.VersionGroupStub

@Entity(
    tableName = "version_stub", foreignKeys = [
        ForeignKey(
            entity = VersionGroupStub::class,
            parentColumns = ["id"],
            childColumns = ["versionGroupId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class VersionStub(
    @PrimaryKey val id: Int,
    val versionGroupId: Int?,
    val isIgnored: Boolean,
    override val updatedAt: Long = TimestampedEntity.generateTimestamp()
) : TimestampedEntity
