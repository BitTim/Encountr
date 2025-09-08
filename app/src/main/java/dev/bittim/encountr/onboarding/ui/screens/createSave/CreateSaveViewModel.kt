/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       CreateSaveViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   08.09.25, 00:53
 */

package dev.bittim.encountr.onboarding.ui.screens.createSave

import android.app.Application
import androidx.lifecycle.AndroidViewModel
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

class CreateSaveViewModel(
    private val application: Application,
    private val configStateHolder: ConfigStateHolder,
    private val definitionRepository: DefinitionRepository,
    private val saveRepository: SaveRepository
) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(CreateSaveState())
    val state = _state.asStateFlow()

    var fetchGamesByGenJob: Job? = null

    init {
        viewModelScope.launch {
            val generationCount = PokeApi.getGenerationList(0, 1).count
            _state.update { it.copy(generations = generationCount) }

            onGenChanged(1)
        }
    }

    fun onGenChanged(generationId: Int) {
        fetchGamesByGenJob?.cancel()
        fetchGamesByGenJob = viewModelScope.launch {
            _state.update { it.copy(games = null) }

            val generation = PokeApi.getGeneration(generationId)
            val games = generation.versionGroups.flatMap { vgHandle ->
                val versionGroup = PokeApi.getVersionGroup(vgHandle.id)
                val versions = versionGroup.versions.mapNotNull { vHandle ->
                    PokeApi.getVersion(vHandle.id)
                        .takeIf { !definitionRepository.checkIgnored(it.id) }
                }

                versions.map { version ->
                    val iconDefinition = definitionRepository.getIconByGame(version.id)
                    val imageUrl = iconDefinition?.let {
                        val rawSprites = PokeApi.getPokemonVariety(it.pokemon).sprites
                        val result =
                            mapPokemonSpriteVersion(rawSprites, version)

                        when (result) {
                            is Result.Ok -> result.data
                            is Result.Err -> throw Exception(
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
                        }.frontDefault
                    }

                    val languageName = configStateHolder.configState.value.languageName
                    val localizedName = version.names.find {
                        it.language.name == (languageName ?: Constants.DEFAULT_LANG_NAME)
                    }?.name ?: version.name
                    val localizedGeneration = generation.names.find {
                        it.language.name == (languageName ?: Constants.DEFAULT_LANG_NAME)
                    }?.name ?: generation.name

                    Game(
                        id = version.id,
                        localizedName = localizedName,
                        localizedGeneration = localizedGeneration,
                        imageUrl = imageUrl
                    )
                }
            }

            _state.update { it.copy(games = games) }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun onContinue(gameId: Int, name: String, navNext: () -> Unit) {
        if (gameId == 0) return

        viewModelScope.launch {
            val save = saveRepository.create(name, gameId)
            configStateHolder.setCurrentSaveUuid(save.id)
            launch(Dispatchers.Main) { navNext() }
        }
    }
}