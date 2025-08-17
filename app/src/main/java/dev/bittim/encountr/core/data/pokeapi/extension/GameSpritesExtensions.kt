/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GameSpritesExtensions.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.08.25, 21:53
 */

package dev.bittim.encountr.core.data.pokeapi.extension

import co.pokeapi.pokekotlin.model.GameSprites
import dev.bittim.encountr.core.domain.model.pokeapi.MappedPokemonSprites

fun GameSprites.toMappedSprites(): MappedPokemonSprites {
    return MappedPokemonSprites(
        animated = this.animated?.toMappedSprites(),
        frontDefault = this.frontTransparent ?: this.frontDefault,
        frontShiny = this.frontShinyTransparent ?: this.frontShiny,
        backDefault = this.backTransparent ?: this.backDefault,
        backShiny = this.backShinyTransparent ?: this.backShiny,
        frontFemale = this.frontFemale,
        frontShinyFemale = this.frontShinyFemale,
        backFemale = this.backFemale,
        backShinyFemale = this.backShinyFemale,
    )
}