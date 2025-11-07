/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionGroupFull.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.versionGroup

import androidx.room.Embedded
import androidx.room.Relation
import dev.bittim.encountr.core.data.api.local.entity.base.ExpirableEntity
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionEntity
import dev.bittim.encountr.core.data.api.local.entity.base.versionGroup.VersionGroupEntity
import dev.bittim.encountr.core.data.api.local.entity.junction.VersionGroupPokedexJunction
import dev.bittim.encountr.core.domain.model.api.Handle

data class VersionGroupFull(
    @Embedded val versionGroup: VersionGroupEntity,
    @Relation(
        entity = VersionGroupPokedexJunction::class,
        parentColumn = "id",
        entityColumn = "versionGroupId",
        projection = ["pokedexId"]
    ) val pokedexIds: List<Int>,
    @Relation(
        entity = VersionEntity::class,
        parentColumn = "id",
        entityColumn = "versionGroupId",
        projection = ["id"]
    ) val versionIds: List<Int>
) : ExpirableEntity by versionGroup {
    fun toModel() = versionGroup.toModel(
        versions = versionIds.map { Handle(it) },
        pokedexes = pokedexIds.map { Handle(it) }
    )
}