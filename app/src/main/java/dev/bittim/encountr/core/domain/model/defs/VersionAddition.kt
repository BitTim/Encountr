/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionAddition.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 19:42
 */

package dev.bittim.encountr.core.domain.model.defs

import dev.bittim.encountr.core.domain.model.api.pokemon.PokemonSpriteVariant
import dev.bittim.encountr.core.domain.model.api.type.TypeSpriteVariant

data class VersionAddition(
    val versionId: Int,
    val imageUrl: String?,
    val pokemonSpriteVariant: PokemonSpriteVariant,
    val typeSpriteVariant: TypeSpriteVariant?
)
