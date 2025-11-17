/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonListViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 02:31
 */

package dev.bittim.encountr.content.ui.screens.pokemon.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bittim.encountr.core.domain.model.api.version.Version
import dev.bittim.encountr.core.domain.useCase.api.ObservePokedexIdsByVersion
import dev.bittim.encountr.core.domain.useCase.ui.ObservePokedexName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class, ExperimentalCoroutinesApi::class)
class PokemonListViewModel(
    private val observePokedexIdsByVersion: ObservePokedexIdsByVersion,
    private val observePokedexNameUseCase: ObservePokedexName
) : ViewModel() {
    private val _state = MutableStateFlow(PokemonListState())
    val state = _state.asStateFlow()

    var pokedexFetchJob: Job? = null

    fun setVersion(version: Version?) {
        _state.update { it.copy(version = version) }
        if (version == null) return

        pokedexFetchJob?.cancel()
        pokedexFetchJob = viewModelScope.launch(Dispatchers.IO) {
            observePokedexIdsByVersion(version.id).collectLatest { pokedexIds ->
                _state.update { it.copy(pokedexIds = pokedexIds) }
            }
        }
    }

    fun observePokedexName(pokedexId: Int): Flow<String> {
        return observePokedexNameUseCase(pokedexId)
    }
}