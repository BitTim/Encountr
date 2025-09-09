/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonListViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   09.09.25, 03:51
 */

package dev.bittim.encountr.content.ui.screens.pokemon.list

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.R
import dev.bittim.encountr.core.data.config.ConfigStateHolder
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepository
import dev.bittim.encountr.core.data.pokeapi.GameError
import dev.bittim.encountr.core.data.pokeapi.mapping.mapPokemonSpriteVersion
import dev.bittim.encountr.core.data.user.repo.SaveRepository
import dev.bittim.encountr.core.di.Constants
import dev.bittim.encountr.core.domain.error.Result
import dev.bittim.encountr.core.ui.util.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class, ExperimentalCoroutinesApi::class)
class PokemonListViewModel(
    private val application: Application,
    private val configStateHolder: ConfigStateHolder,
    private val definitionRepository: DefinitionRepository,
    private val saveRepository: SaveRepository
) : ViewModel() {
    private val _state = MutableStateFlow(PokemonListState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            configStateHolder.configState.map { it.currentSaveUuid }.filterNotNull().flatMapLatest {
                saveRepository.get(it)
            }.filterNotNull().collectLatest { save ->
                val version = PokeApi.getVersion(save.game)
                _state.update { it.copy(version = version) }

                Log.d("PokemonListViewModel", "Version: $version")
                val linkedVersionGroupIds =
                    definitionRepository.getLinkedVersionGroupByParent(version.versionGroup.id)?.linked
                        ?: emptyList()

                Log.d("PokemonListViewModel", "Linked version groups: $linkedVersionGroupIds")
                val pokedexes =
                    listOf(version.versionGroup.id).union(linkedVersionGroupIds).flatMap { id ->
                        PokeApi.getVersionGroup(id).pokedexes.map { handle ->
                            val pokedex = PokeApi.getPokedex(handle.id)
                            Log.d("PokemonListViewModel", "Pokedex: $pokedex")

                            val localizedPokedexName =
                                pokedex.names.find { it.language.name == configStateHolder.configState.value.languageName }?.name
                                    ?: pokedex.names.find { it.language.name == Constants.DEFAULT_LANG_NAME }?.name
                                    ?: pokedex.name

                            val pokemon = pokedex.pokemonEntries
                            Log.d("PokemonListViewModel", "Pokemon: $pokemon")

                            Pokedex(
                                id = handle.id,
                                name = localizedPokedexName,
                                pokemon = pokemon,
                            )
                        }
                    }

                Log.d("PokemonListViewModel", "Pokedexes: $pokedexes")
                _state.update { it.copy(pokedexes = pokedexes) }
            }
        }
    }

    fun fetchPokemon(id: Int) {
        if (_state.value.version == null) return

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val species = PokeApi.getPokemonSpecies(id)
                Log.d("PokemonListViewModel", "Species: $species")

                val defaultVariety =
                    species.varieties.find { it.isDefault }?.variety?.id?.let {
                        PokeApi.getPokemonVariety(it)
                    } ?: return@withContext
                Log.d("PokemonListViewModel", "Default variety: $defaultVariety")

                val localizedPokemonName = species.names.find {
                    it.language.name == configStateHolder.configState.value.languageName
                }?.name
                    ?: species.names.find { it.language.name == Constants.DEFAULT_LANG_NAME }?.name
                    ?: species.name

                val imageResult = mapPokemonSpriteVersion(
                    defaultVariety.sprites,
                    _state.value.version!!
                )
                val imageUrl = when (imageResult) {
                    is Result.Ok -> imageResult.data
                    is Result.Err -> throw Exception(
                        when (imageResult.error) {
                            is GameError.InvalidVersionGroup -> UiText.StringResource(
                                R.string.error_invalid_version_group,
                                imageResult.error.versionGroup
                            ).asString(application.applicationContext)

                            is GameError.InvalidVersion -> UiText.StringResource(
                                R.string.error_invalid_version,
                                imageResult.error.version
                            ).asString(application.applicationContext)

                            else -> UiText.StringResource(R.string.error_unknown)
                                .asString(application.applicationContext)
                        }
                    )
                }.frontDefault ?: return@withContext
                Log.d("PokemonListViewModel", "Image URL: $imageUrl")

                val pokemon = Pokemon(
                    id = id,
                    name = localizedPokemonName,
                    height = "${defaultVariety.height.toFloat().div(10)} m",
                    weight = "${defaultVariety.weight.toFloat().div(10)} kg",
                    imageUrl = imageUrl,
                    types = defaultVariety.types.map { it.type.name },
                )

                Log.d("PokemonListViewModel", "Pokemon: $pokemon")
                _state.update {
                    val newPokemon = it.pokemon.toMutableMap()
                    newPokemon[id] = pokemon
                    it.copy(pokemon = newPokemon)
                }

                Log.d("PokemonListViewModel", "Pokemon in state: ${_state.value.pokemon}")
            }
        }
    }
}