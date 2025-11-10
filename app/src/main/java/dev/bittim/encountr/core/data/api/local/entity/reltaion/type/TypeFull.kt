/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TypeFull.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.type

import androidx.room.Embedded
import androidx.room.Relation
import dev.bittim.encountr.core.data.api.local.entity.base.TimestampedEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeSpriteEntity

data class TypeFull(
    @Embedded val type: TypeEntity,
    @Relation(
        entity = TypeLocalizedNameEntity::class,
        parentColumn = "id",
        entityColumn = "typeId"
    ) val typeLocalizedNames: List<TypeLocalizedNameEntity>,
    @Relation(
        entity = TypeSpriteEntity::class,
        parentColumn = "id",
        entityColumn = "typeId"
    ) val typeSprites: List<TypeSpriteEntity>
) : TimestampedEntity by type {
    fun toModel() = type.toModel(
        localizedNames = typeLocalizedNames.map { it.toModel() },
        typeSprites = typeSprites.map { it.toModel() }
    )
}
