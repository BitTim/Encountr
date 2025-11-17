/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonSprites.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 19:32
 */

package dev.bittim.encountr.core.domain.model.api.pokemon

data class PokemonSprites(
    val variant: PokemonSpriteVariant,

    val frontDefault: String?,
    val frontShiny: String?,
    val backDefault: String?,
    val backShiny: String?,

    val frontFemale: String?,
    val frontShinyFemale: String?,
    val backFemale: String?,
    val backShinyFemale: String?,
)