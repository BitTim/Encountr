/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonListViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.09.25, 00:53
 */

package dev.bittim.encountr.content.ui.screens.pokemon.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bittim.encountr.core.data.config.ConfigStateHolder
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepository
import dev.bittim.encountr.core.data.pokeapi.repo.PokedexRepository
import dev.bittim.encountr.core.data.pokeapi.repo.PokemonOverviewRepository
import dev.bittim.encountr.core.data.pokeapi.repo.VersionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class, ExperimentalCoroutinesApi::class)
class PokemonListViewModel(
    private val configStateHolder: ConfigStateHolder,
    private val definitionRepository: DefinitionRepository,
    private val versionRepository: VersionRepository,
    private val pokedexRepository: PokedexRepository,
    private val pokemonOverviewRepository: PokemonOverviewRepository
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
                _state.update {
                    val version =
                        versionRepository.get(config?.currentSave?.version ?: return@collect)
                    it.copy(languageName = config.languageName, version = version)
                }
            }
        }

        pokedexFetchJob?.cancel()
        pokedexFetchJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                configStateHolder.state.collectLatest { config ->
                    if (config == null) return@collectLatest

                    val version =
                        versionRepository.get(config.currentSave.version) ?: return@collectLatest
                    val linkedVersionGroup =
                        definitionRepository.getLinkedVersionGroupByParent(version.versionGroupId)

                    val versionGroupIds = mutableListOf(version.versionGroupId).union(
                        linkedVersionGroup?.linked ?: emptyList()
                    )

                    val pokedexes = coroutineScope {
                        versionGroupIds.map {
                            async(Dispatchers.IO) { pokedexRepository.getByVersionGroupId(it) }
                        }
                    }.awaitAll().flatten()

                    onPokedexChanged(pokedexes.first().id)
                    _state.update { it.copy(pokedexes = pokedexes) }
                }
            }
        }
    }

    fun onPokedexChanged(id: Int) {
        pokemonOverviewFetchJob?.cancel()
        pokemonOverviewFetchJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _state.update { it.copy(pokemon = null) }

                val pokemon = pokemonOverviewRepository.getByPokedex(id)

                _state.update { it.copy(pokemon = pokemon) }
            }
        }
    }
}