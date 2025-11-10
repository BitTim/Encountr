/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GenerationFull.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.generation

import androidx.room.Embedded
import androidx.room.Relation
import dev.bittim.encountr.core.data.api.local.entity.base.TimestampedEntity
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.versionGroup.VersionGroupStub

data class GenerationFull(
    @Embedded val generation: GenerationEntity,
    @Relation(
        entity = GenerationLocalizedNameEntity::class,
        parentColumn = "id",
        entityColumn = "generationId"
    ) val localizedNames: List<GenerationLocalizedNameEntity>,
    @Relation(
        entity = VersionGroupStub::class,
        parentColumn = "id",
        entityColumn = "generationId",
        projection = ["id"]
    ) val versionGroupIds: List<Int>
) : TimestampedEntity by generation {
    fun toModel() = generation.toModel(
        localizedNames = localizedNames.map { it.toModel() },
        versionGroups = versionGroupIds
    )
}
