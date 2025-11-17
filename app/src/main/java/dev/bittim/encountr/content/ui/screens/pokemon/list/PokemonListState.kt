/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonListState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 02:31
 */

package dev.bittim.encountr.content.ui.screens.pokemon.list

import dev.bittim.encountr.core.domain.model.api.version.Version

data class PokemonListState(
    val version: Version? = null,

    val pokedexIds: List<Int> = emptyList(),
)
