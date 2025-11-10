/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonSpritesExtensions.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.extension

import co.pokeapi.pokekotlin.model.PokemonSprites
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonSpritesEntity
import dev.bittim.encountr.core.domain.model.api.pokemon.PokemonSpriteVariant

fun PokemonSprites.toEntity(
    pokemonId: Int,
    pokemonSpriteVariant: PokemonSpriteVariant
): PokemonSpritesEntity {
    return PokemonSpritesEntity(
        pokemonId = pokemonId,
        pokemonSpriteVariant = pokemonSpriteVariant,

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