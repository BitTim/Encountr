/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonDetailsViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.08.25, 02:32
 */

package dev.bittim.encountr.content.pokemon.ui.screen.details

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PokemonDetailsViewModel(
    private val id: Int
) : ViewModel() {
    private val _state = MutableStateFlow(
        PokemonDetailsState(
            id = id
        )
    )
    val state = _state.asStateFlow()
}