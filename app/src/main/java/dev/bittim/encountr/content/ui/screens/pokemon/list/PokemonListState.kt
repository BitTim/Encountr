/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonListState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.content.ui.screens.pokemon.list

import co.pokeapi.pokekotlin.model.Pokedex
import dev.bittim.encountr.core.domain.model.api.pokemon.Pokemon
import dev.bittim.encountr.core.domain.model.api.version.Version

data class PokemonListState(
    val languageId: Int? = null,
    val version: Version? = null,

    val pokedexes: List<Pokedex>? = null,
    val pokemon: List<Pokemon>? = null,
    val filteredPokemon: List<Pokemon>? = null,
)
