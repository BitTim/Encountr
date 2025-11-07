/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GenerationFull.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.generation

import androidx.room.Embedded
import androidx.room.Relation
import dev.bittim.encountr.core.data.api.local.entity.base.ExpirableEntity
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationEntity
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.versionGroup.VersionGroupEntity
import dev.bittim.encountr.core.domain.model.api.Handle

data class GenerationFull(
    @Embedded val generation: GenerationEntity,
    @Relation(
        entity = GenerationLocalizedNameEntity::class,
        parentColumn = "id",
        entityColumn = "generationId"
    ) val localizedNames: List<GenerationLocalizedNameEntity>,
    @Relation(
        entity = VersionGroupEntity::class,
        parentColumn = "id",
        entityColumn = "generationId",
        projection = ["id"]
    ) val versionGroupIds: List<Int>
) : ExpirableEntity by generation {
    fun toModel() = generation.toModel(
        localizedNames = localizedNames.map { it.toModel() },
        versionGroups = versionGroupIds.map { Handle(it) }
    )
}
