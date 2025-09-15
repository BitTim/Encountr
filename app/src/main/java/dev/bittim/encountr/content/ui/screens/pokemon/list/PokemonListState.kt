/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonListState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.09.25, 00:53
 */

package dev.bittim.encountr.content.ui.screens.pokemon.list

import co.pokeapi.pokekotlin.model.Pokedex
import dev.bittim.encountr.core.domain.model.pokeapi.PokemonOverview
import dev.bittim.encountr.core.domain.model.pokeapi.Version

data class PokemonListState(
    val languageName: String? = null,
    val version: Version? = null,

    val pokedexes: List<Pokedex>? = null,
    val pokemon: List<PokemonOverview?>? = null
)
