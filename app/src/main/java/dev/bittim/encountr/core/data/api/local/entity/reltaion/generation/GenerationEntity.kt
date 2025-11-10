/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GenerationEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.generation

import androidx.room.Embedded
import androidx.room.Relation
import dev.bittim.encountr.core.data.api.local.entity.base.TimestampedEntity
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationStub
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString

data class GenerationEntity(
    @Embedded val stub: GenerationStub,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    ) val detail: GenerationDetailEntity
) : TimestampedEntity by stub {
    fun toModel(localizedNames: List<LocalizedString>, versionGroups: List<Int>) = detail.toModel(
        localizedNames = localizedNames,
        versionGroups = versionGroups
    )
}
