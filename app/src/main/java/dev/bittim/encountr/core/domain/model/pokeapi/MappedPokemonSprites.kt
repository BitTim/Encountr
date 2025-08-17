/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       MappedPokemonSprites.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.08.25, 17:57
 */

package dev.bittim.encountr.core.domain.model.pokeapi

data class MappedPokemonSprites(
    val animated: MappedPokemonSprites? = null,

    val frontDefault: String?,
    val frontShiny: String?,
    val backDefault: String?,
    val backShiny: String?,

    val frontFemale: String?,
    val frontShinyFemale: String?,
    val backFemale: String?,
    val backShinyFemale: String?,
)