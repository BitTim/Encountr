/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GameSpritesExtensions.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.extension

import co.pokeapi.pokekotlin.model.GameSprites
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonSpritesEntity
import dev.bittim.encountr.core.domain.model.api.pokemon.PokemonSpriteVariant

fun GameSprites.toEntity(
    pokemonId: Int,
    pokemonSpriteVariant: PokemonSpriteVariant
): PokemonSpritesEntity {
    return PokemonSpritesEntity(
        pokemonId = pokemonId,
        pokemonSpriteVariant = pokemonSpriteVariant,

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