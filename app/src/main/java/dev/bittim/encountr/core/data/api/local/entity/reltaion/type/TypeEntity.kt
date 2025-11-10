/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TypeEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.type

import androidx.room.Embedded
import androidx.room.Relation
import dev.bittim.encountr.core.data.api.local.entity.base.TimestampedEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeStub
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString
import dev.bittim.encountr.core.domain.model.api.type.TypeSprite

data class TypeEntity(
    @Embedded val stub: TypeStub,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    ) val detail: TypeDetailEntity
) : TimestampedEntity by stub {
    fun toModel(
        localizedNames: List<LocalizedString>,
        typeSprites: List<TypeSprite>
    ) = detail.toModel(
        localizedNames = localizedNames,
        typeSprites = typeSprites
    )
}
