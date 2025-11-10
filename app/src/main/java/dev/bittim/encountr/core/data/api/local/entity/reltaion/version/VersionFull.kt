/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionFull.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.version

import androidx.room.Embedded
import androidx.room.Relation
import dev.bittim.encountr.core.data.api.local.entity.base.TimestampedEntity
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionLocalizedNameEntity

data class VersionFull(
    @Embedded val version: VersionEntity,
    @Relation(
        entity = VersionLocalizedNameEntity::class,
        parentColumn = "id",
        entityColumn = "versionId"
    ) val localizedNames: List<VersionLocalizedNameEntity>
) : TimestampedEntity by version {
    fun toModel() = version.toModel(localizedNames.map { it.toModel() })
}
