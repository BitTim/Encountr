/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonListViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 20:21
 */

package dev.bittim.encountr.content.ui.screens.pokemon.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bittim.encountr.content.ui.components.PokemonCardState
import dev.bittim.encountr.core.domain.model.api.version.Version
import dev.bittim.encountr.core.domain.useCase.api.ObservePokedexIdsByVersion
import dev.bittim.encountr.core.domain.useCase.api.ObservePokemonIdsByPokedex
import dev.bittim.encountr.core.domain.useCase.ui.ObservePokedexName
import dev.bittim.encountr.core.domain.useCase.ui.ObservePokemonCardState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class, ExperimentalCoroutinesApi::class)
class PokemonListViewModel(
    private val observePokedexIdsByVersion: ObservePokedexIdsByVersion,
    private val observePokedexNameUseCase: ObservePokedexName,
    private val observePokemonIdsByPokedex: ObservePokemonIdsByPokedex,
    private val observePokemonCardState: ObservePokemonCardState
) : ViewModel() {
    private val _state = MutableStateFlow(PokemonListState())
    val state = _state.asStateFlow()

    var pokedexFetchJob: Job? = null
    var pokemonIdsFetchJob: Job? = null

    fun setVersion(version: Version?) {
        _state.update { it.copy(version = version) }
        if (version == null) return

        pokedexFetchJob?.cancel()
        pokedexFetchJob = viewModelScope.launch(Dispatchers.IO) {
            observePokedexIdsByVersion(version.id).collectLatest { pokedexIds ->
                _state.update { it.copy(pokedexIds = pokedexIds) }
                onPokedexChanged(pokedexIds.first())
            }
        }
    }

    fun observePokedexName(pokedexId: Int): Flow<String> {
        return observePokedexNameUseCase(pokedexId)
    }

    fun onPokedexChanged(pokedexId: Int) {
        pokemonIdsFetchJob?.cancel()
        pokemonIdsFetchJob = viewModelScope.launch(Dispatchers.IO) {
            observePokemonIdsByPokedex(pokedexId).collectLatest { pokemonIds ->
                _state.update { it.copy(pokedexId = pokedexId, pokemonIds = pokemonIds) }
            }
        }
    }

    fun observePokemon(pokemonId: Int?): Flow<PokemonCardState?> {
        return pokemonId?.let {
            state.flatMapLatest { screenState ->
                if (screenState.version != null && screenState.pokedexId != null) observePokemonCardState(
                    pokemonId,
                    screenState.pokedexId,
                    screenState.version.pokemonSpriteVariant,
                    screenState.version.typeSpriteVariant
                )
                else flowOf(null)
            }
        } ?: flowOf(null)
    }
}