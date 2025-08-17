/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       CreateSaveViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.08.25, 03:20
 */

package dev.bittim.encountr.onboarding.ui.screens.createSave

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.R
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepository
import dev.bittim.encountr.core.data.pokeapi.GameError
import dev.bittim.encountr.core.data.pokeapi.mapping.mapPokemonVersionSprite
import dev.bittim.encountr.core.domain.Paginator
import dev.bittim.encountr.core.ui.util.UiText
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class CreateSaveViewModel(
    private val application: Application,
    private val definitionRepository: DefinitionRepository,
) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(CreateSaveState())
    val state = _state.asStateFlow()

    private val pageSize = 10
    private val paginator = Paginator(
        initialKey = 0,
        onLoadUpdated = { isLoading ->
            _state.update { it.copy(isLoading = isLoading) }
        },
        onRequest = { offset ->
            return@Paginator try {
                val response = PokeApi.getVersionList(offset, pageSize)
                val games = response.results.mapNotNull { handle ->
                    val version = PokeApi.getVersion(handle.id)
                    val versionGroup = PokeApi.getVersionGroup(version.versionGroup.id)
                    val generation = PokeApi.getGeneration(versionGroup.generation.id)

                    if (definitionRepository.checkIgnored(handle.id)) return@mapNotNull null
                    val iconDefinition = definitionRepository.getIconByGame(handle.id)

                    val imageUrl = iconDefinition?.let {
                        val rawSprites = PokeApi.getPokemonVariety(it.pokemon).sprites
                        val result =
                            mapPokemonVersionSprite(rawSprites, generation, versionGroup, version)
                        val sprites = when (result) {
                            is dev.bittim.encountr.core.domain.error.Result.Ok -> result.data
                            is dev.bittim.encountr.core.domain.error.Result.Err -> throw Exception(
                                when (result.error) {
                                    is GameError.InvalidVersionGroup -> UiText.StringResource(
                                        R.string.error_invalid_version_group,
                                        result.error.versionGroup
                                    ).asString(application.applicationContext)

                                    is GameError.InvalidVersion -> UiText.StringResource(
                                        R.string.error_invalid_version,
                                        result.error.version
                                    ).asString(application.applicationContext)

                                    else -> UiText.StringResource(R.string.error_unknown)
                                        .asString(application.applicationContext)
                                }
                            )
                        }

                        sprites.frontDefault
                    }

                    Game(
                        id = version.id,
                        localizedName = version.name,
                        localizedGeneration = generation.name,
                        imageUrl = imageUrl
                    )
                }

                Result.success(Pair(response.count, games))
            } catch (e: Exception) {
                coroutineContext.ensureActive()
                Result.failure(e)
            }
        },
        getNextKey = { currentKey, _ -> currentKey + pageSize },
        onError = { throwable ->
            _state.update { it.copy(error = throwable?.localizedMessage ?: throwable?.message) }
        },
        onSuccess = { item, newKey ->
            _state.update { it.copy(games = if (it.games == null) item.second else it.games + item.second) }
        },
        checkFinished = { currentKey, item ->
            currentKey >= item.first
        }
    )

    init {
        loadNext()
    }

    fun loadNext() {
        viewModelScope.launch {
            paginator.next()
        }
    }
}