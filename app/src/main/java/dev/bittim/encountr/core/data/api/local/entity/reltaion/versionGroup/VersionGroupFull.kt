/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionGroupFull.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.versionGroup

import androidx.room.Embedded
import androidx.room.Relation
import dev.bittim.encountr.core.data.api.local.entity.base.TimestampedEntity
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionStub
import dev.bittim.encountr.core.data.api.local.entity.junction.VersionGroupPokedexJunction

data class VersionGroupFull(
    @Embedded val versionGroup: VersionGroupEntity,
    @Relation(
        entity = VersionGroupPokedexJunction::class,
        parentColumn = "id",
        entityColumn = "versionGroupId",
        projection = ["pokedexId"]
    ) val pokedexIds: List<Int>,
    @Relation(
        entity = VersionStub::class,
        parentColumn = "id",
        entityColumn = "versionGroupId",
        projection = ["id"]
    ) val versionIds: List<Int>
) : TimestampedEntity by versionGroup {
    fun toModel() = versionGroup.toModel(
        versionIds = versionIds,
        pokedexIds = pokedexIds
    )
}