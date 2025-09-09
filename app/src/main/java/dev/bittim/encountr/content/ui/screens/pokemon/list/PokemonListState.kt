/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonListState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.09.25, 00:07
 */

package dev.bittim.encountr.content.ui.screens.pokemon.list

import co.pokeapi.pokekotlin.model.Pokedex
import co.pokeapi.pokekotlin.model.Version
import dev.bittim.encountr.content.ui.components.PokemonCardData

data class Pokemon(
    val id: Int,
    val name: String,
    val height: String,
    val weight: String,
    val imageUrl: String,
    val types: List<String>,
) {
    fun toPokemonCardData(entryNumber: Int) = PokemonCardData(
        id = id,
        entryNumber = entryNumber,
        name = name,
        height = height,
        weight = weight,
        imageUrl = imageUrl,
        types = types
    )
}

data class PokemonListState(
    val version: Version? = null,
    val pokedexes: List<Pokedex>? = null,
    val pokemon: List<PokemonCardData?>? = null
)
