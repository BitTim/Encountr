/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonListViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.09.25, 19:25
 */

package dev.bittim.encountr.content.ui.screens.pokemon.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.pokeapi.pokekotlin.PokeApi
import co.pokeapi.pokekotlin.model.Pokedex
import dev.bittim.encountr.content.ui.components.PokemonCardData
import dev.bittim.encountr.core.data.config.ConfigStateHolder
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepository
import dev.bittim.encountr.core.data.pokeapi.mapping.mapPokemonSpriteVersion
import dev.bittim.encountr.core.di.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
) : ViewModel() {
    private val _state = MutableStateFlow(PokemonListState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                configStateHolder.state.collectLatest { config ->
                    if (config == null) return@collectLatest

                    val version = PokeApi.getVersion(config.currentSave.version)
                    val linkedVersionGroup =
                        definitionRepository.getLinkedVersionGroupByParent(version.versionGroup.id)

                    val versionGroupIds = mutableListOf(version.versionGroup.id).union(
                        linkedVersionGroup?.linked ?: emptyList()
                    )

                    val pokedexes = coroutineScope {
                        versionGroupIds.map {
                            async(Dispatchers.IO) { fetchPokedexesFromVersionGroup(it) }
                        }
                    }.awaitAll().flatten()

                    onPokedexChanged(0)
                    _state.update { it.copy(version = version, pokedexes = pokedexes) }
                }
            }
        }
    }

    suspend fun fetchPokedexesFromVersionGroup(id: Int): List<Pokedex> {
        val versionGroup = PokeApi.getVersionGroup(id)
        return coroutineScope {
            versionGroup.pokedexes.map {
                async(Dispatchers.IO) { PokeApi.getPokedex(it.id) }
            }
        }.awaitAll()
    }

    suspend fun fetchPokemon(id: Int, entryNumber: Int): PokemonCardData? {
        val version = _state.value.version ?: return null

        val pokemonSpecies = PokeApi.getPokemonSpecies(id)
        val defaultVariety =
            PokeApi.getPokemonVariety(pokemonSpecies.varieties.first { it.isDefault }.variety.id)

        val imageUrl =
            mapPokemonSpriteVersion(defaultVariety.sprites, version).frontDefault

        return PokemonCardData(
            id = pokemonSpecies.id,
            entryNumber = entryNumber,
            name = pokemonSpecies.names.first {
                it.language.name == (configStateHolder.state.value?.languageName
                    ?: Constants.DEFAULT_LANG_NAME)
            }.name,
            height = "${defaultVariety.height.toFloat().div(10)} m",
            weight = "${defaultVariety.weight.toFloat().div(10)} kg",
            imageUrl = imageUrl,
            types = defaultVariety.types.map { it.type.name }
        )
    }

    fun onPokedexChanged(index: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val pokedex = _state.value.pokedexes?.get(index) ?: return@withContext
                _state.update { it.copy(pokemon = null) }

                val pokemon = coroutineScope {
                    pokedex.pokemonEntries.map {
                        async(Dispatchers.IO) {
                            fetchPokemon(
                                it.pokemonSpecies.id,
                                it.entryNumber
                            )
                        }
                    }
                }.awaitAll()

                _state.update { it.copy(pokemon = pokemon) }
            }
        }
    }
}