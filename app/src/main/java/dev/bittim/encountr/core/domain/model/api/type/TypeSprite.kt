/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TypeSprite.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 19:33
 */

package dev.bittim.encountr.core.domain.model.api.type

data class TypeSprite(
    val variant: TypeSpriteVariant,
    val imageUrl: String?,
)
