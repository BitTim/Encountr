/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonListState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   08.09.25, 02:22
 */

package dev.bittim.encountr.content.ui.screens.pokemon.list

data class Pokemon(
    val id: Int,
    val entryNumber: Int,
    val name: String,
    val height: String,
    val weight: String,
    val imageUrl: String,
    val types: List<String>,
)

data class Pokedex(
    val id: Int,
    val name: String,
    val pokemon: List<Pokemon>,
)

data class PokemonListState(
    val selectedPokedexId: Int? = null,
    val pokedexes: List<Pokedex>? = null,
)
