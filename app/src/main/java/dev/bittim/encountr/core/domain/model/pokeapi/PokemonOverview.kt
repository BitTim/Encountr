/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonOverview.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.09.25, 00:53
 */

package dev.bittim.encountr.core.domain.model.pokeapi

import co.pokeapi.pokekotlin.model.PokemonSprites

data class PokemonOverview(
    val id: Int,
    val entryNumbers: List<PokedexEntry>,
    val name: String,
    val localizedNames: List<LocalizedString>,
    val height: String,
    val weight: String,
    val sprites: PokemonSprites,
    val types: List<Type>,
)
