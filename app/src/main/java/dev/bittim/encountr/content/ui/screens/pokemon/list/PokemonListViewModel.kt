/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonListViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:15
 */

package dev.bittim.encountr.content.ui.screens.pokemon.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bittim.encountr.core.data.api.repo.version.VersionRepository
import dev.bittim.encountr.core.data.config.ConfigStateHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class, ExperimentalCoroutinesApi::class)
class PokemonListViewModel(
    private val configStateHolder: ConfigStateHolder,
    private val versionRepository: VersionRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(PokemonListState())
    val state = _state.asStateFlow()

    var configFetchJob: Job? = null
    var pokedexFetchJob: Job? = null
    var pokemonOverviewFetchJob: Job? = null

    init {
        configFetchJob?.cancel()
        configFetchJob = viewModelScope.launch {
            configStateHolder.state.collect { config ->
                versionRepository.get(config?.currentSave?.version ?: return@collect)
                    .stateIn(viewModelScope, WhileSubscribed(5000), null)
                    .collectLatest { version ->
                        _state.update {
                            it.copy(
                                languageId = config.languageId,
                                version = version
                            )
                        }
                    }
            }
        }

        pokedexFetchJob?.cancel()
        pokedexFetchJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                configStateHolder.state.collectLatest { config ->
                    if (config == null) return@collectLatest

                    // TODO
                    val pokedexes = null

                    _state.update { it.copy(pokedexes = pokedexes) }
                }
            }
        }
    }

    fun onPokedexChanged(id: Int, searchQuery: String) {
        pokemonOverviewFetchJob?.cancel()
        pokemonOverviewFetchJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _state.update { it.copy(filteredPokemon = null, pokemon = null) }

                // TODO
                val pokemon = null

                _state.update { it.copy(pokemon = pokemon) }
                applyFilter(searchQuery)
            }
        }
    }

    fun applyFilter(searchQuery: String) {
        val filteredPokemon = _state.value.pokemon?.filter {
            it.name.contains(
                searchQuery,
                true
            ) || it.localizedNames.any { name -> name.value.contains(searchQuery, true) }
        }

        _state.update { it.copy(filteredPokemon = filteredPokemon) }
    }
}