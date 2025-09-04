/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonListState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   04.09.25, 23:26
 */

package dev.bittim.encountr.content.ui.screens.pokemon.list

import dev.bittim.encountr.core.domain.model.user.Pokemon

data class PokemonListState(
    val pokemon: List<Pokemon>? = null, // TODO: Replace Type
)
