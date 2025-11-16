/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionGroupEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.11.25, 03:07
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.versionGroup

import androidx.room.Embedded
import androidx.room.Relation
import dev.bittim.encountr.core.data.api.local.entity.base.CombinedEntity
import dev.bittim.encountr.core.data.api.local.entity.base.versionGroup.VersionGroupDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.versionGroup.VersionGroupStub

data class VersionGroupEntity(
    @Embedded override val stub: VersionGroupStub,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    ) override val detail: VersionGroupDetailEntity?
) : CombinedEntity {
    fun toModel(
        versionIds: List<Int>,
        pokedexIds: List<Int>
    ) = detail?.toModel(
        generationId = stub.generationId,
        versionIds = versionIds,
        pokedexIds = pokedexIds
    )
}
