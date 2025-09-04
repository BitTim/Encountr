/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonListViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   04.09.25, 23:26
 */

package dev.bittim.encountr.content.ui.screens.pokemon.list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PokemonListViewModel : ViewModel() {
    private val _state = MutableStateFlow(PokemonListState())
    val state = _state.asStateFlow()


}