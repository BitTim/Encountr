/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       BlackWhiteSpritesExtensions.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.extension

import co.pokeapi.pokekotlin.model.BlackWhiteSprites
import dev.bittim.encountr.core.data.api.local.entity.base.ExpirableEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonSpritesEntity
import dev.bittim.encountr.core.domain.model.api.pokemon.PokemonSpriteVariant

fun BlackWhiteSprites.toEntity(pokemonId: Int): PokemonSpritesEntity {
    return PokemonSpritesEntity(
        pokemonId = pokemonId,
        expiresAt = ExpirableEntity.calcExpiryTime(),
        pokemonSpriteVariant = PokemonSpriteVariant.BLACK_WHITE,

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