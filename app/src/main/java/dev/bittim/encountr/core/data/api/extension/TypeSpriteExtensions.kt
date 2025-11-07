/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TypeSpriteExtensions.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.extension

import co.pokeapi.pokekotlin.model.TypeSprites
import dev.bittim.encountr.core.data.api.local.entity.base.ExpirableEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeSpriteEntity
import dev.bittim.encountr.core.domain.model.api.type.TypeSpriteVariant

fun TypeSprites.toEntity(typeId: Int, typeSpriteVariant: TypeSpriteVariant): TypeSpriteEntity {
    return TypeSpriteEntity(
        typeId = typeId,
        typeSpriteVariant = typeSpriteVariant,
        expiresAt = ExpirableEntity.calcExpiryTime(),
        imageUrl = nameIcon
    )
}