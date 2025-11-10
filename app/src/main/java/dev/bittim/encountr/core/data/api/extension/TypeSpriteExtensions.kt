/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TypeSpriteExtensions.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.extension

import co.pokeapi.pokekotlin.model.TypeSprites
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeSpriteEntity
import dev.bittim.encountr.core.domain.model.api.type.TypeSpriteVariant

fun TypeSprites.toEntity(typeId: Int, typeSpriteVariant: TypeSpriteVariant): TypeSpriteEntity {
    return TypeSpriteEntity(
        typeId = typeId,
        typeSpriteVariant = typeSpriteVariant,
        imageUrl = nameIcon
    )
}