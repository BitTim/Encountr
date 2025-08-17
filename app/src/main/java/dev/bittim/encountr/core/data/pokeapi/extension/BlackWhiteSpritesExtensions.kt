/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       BlackWhiteSpritesExtensions.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.08.25, 18:30
 */

package dev.bittim.encountr.core.data.pokeapi.extension

import co.pokeapi.pokekotlin.model.BlackWhiteSprites
import dev.bittim.encountr.core.domain.model.pokeapi.MappedPokemonSprites

fun BlackWhiteSprites.toMappedSprites(): MappedPokemonSprites {
    return MappedPokemonSprites(
        animated = this.animated.toMappedSprites(),
        frontDefault = this.frontDefault,
        frontShiny = this.frontShiny,
        backDefault = this.backDefault,
        backShiny = this.backShiny,
        frontFemale = this.frontFemale,
        frontShinyFemale = this.frontShinyFemale,
        backFemale = this.backFemale,
        backShinyFemale = this.backShinyFemale,
    )
}