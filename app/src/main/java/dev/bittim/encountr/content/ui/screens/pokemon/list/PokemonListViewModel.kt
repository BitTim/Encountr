/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonListViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   18.11.25, 05:10
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class, ExperimentalCoroutinesApi::class)
class PokemonListViewModel(
    private val observePokedexIdsByVersion: ObservePokedexIdsByVersion,
    private val observePokedexNameUseCase: ObservePokedexName,
    private val observePokemonIdsByPokedex: ObservePokemonIdsByPokedex,
    private val observePokemonCardState: ObservePokemonCardState,
) : ViewModel() {
    private val _state = MutableStateFlow(PokemonListState())
    val state = _state.asStateFlow()

    private var _firstLoad = true
    private var _pokedexFetchJob: Job? = null
    private var _pokemonIdsFetchJob: Job? = null

    private val _nullPokemonFlow = MutableStateFlow<PokemonCardState?>(null)
    private val _pokemonFlowScopes = ConcurrentHashMap<Int, CoroutineScope>()
    private val _pokemonFlows = ConcurrentHashMap<Int, StateFlow<PokemonCardState?>>()
    private val _sharingStrategy = SharingStarted.Eagerly

    fun setVersion(version: Version?) {
        _state.update { it.copy(version = version) }
        if (version == null) return

        _pokedexFetchJob?.cancel()
        _pokedexFetchJob = viewModelScope.launch(Dispatchers.IO) {
            observePokedexIdsByVersion(version.id).collectLatest { pokedexIds ->
                _state.update { it.copy(pokedexIds = pokedexIds) }
                if (_firstLoad) {
                    onPokedexChanged(pokedexIds.first())
                    _firstLoad = false
                }
            }
        }
    }

    private fun removePokemonFlows(pokemonIds: List<Int>) {
        pokemonIds.forEach { pokemonId ->
            _pokemonFlowScopes.remove(pokemonId)?.cancel()
            _pokemonFlows.remove(pokemonId)
        }
    }

    fun observePokedexName(pokedexId: Int): Flow<String> {
        return observePokedexNameUseCase(pokedexId)
    }

    fun onPokedexChanged(pokedexId: Int) {
        val oldPokemonIds = _state.value.pokemonIds

        _state.update { it.copy(pokedexId = pokedexId, pokemonIds = emptyList()) }
        removePokemonFlows(oldPokemonIds)

        _pokemonIdsFetchJob?.cancel()
        _pokemonIdsFetchJob = viewModelScope.launch(Dispatchers.IO) {
            observePokemonIdsByPokedex(pokedexId).collectLatest { pokemonIds ->
                pokemonIds.forEach { pokemonId -> observePokemon(pokemonId) }
                _state.update { it.copy(pokemonIds = pokemonIds) }
            }
        }
    }

    fun observePokemon(pokemonId: Int?): StateFlow<PokemonCardState?> {
        if (pokemonId == null) return _nullPokemonFlow

        return _pokemonFlows.getOrPut(pokemonId) {
            val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
            _pokemonFlowScopes[pokemonId] = scope

            state.flatMapLatest { screenState ->
                if (screenState.version != null && screenState.pokedexId != null) observePokemonCardState(
                    pokemonId,
                    screenState.pokedexId,
                    screenState.version.pokemonSpriteVariant,
                    screenState.version.typeSpriteVariant
                )
                else flowOf(null)
            }.stateIn(scope, _sharingStrategy, null)
        }
    }
}