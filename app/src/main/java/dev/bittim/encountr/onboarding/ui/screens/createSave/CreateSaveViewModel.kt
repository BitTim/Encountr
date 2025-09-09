/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       CreateSaveViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.09.25, 00:07
 */

package dev.bittim.encountr.onboarding.ui.screens.createSave

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.config.ConfigStateHolder
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepository
import dev.bittim.encountr.core.data.pokeapi.mapping.mapPokemonSpriteVersion
import dev.bittim.encountr.core.data.user.repo.SaveRepository
import dev.bittim.encountr.core.di.Constants
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
                        mapPokemonSpriteVersion(rawSprites, version).frontDefault
                    }

                    val languageName =
                        configStateHolder.state.value?.languageName ?: Constants.DEFAULT_LANG_NAME
                    val localizedName = version.names.find {
                        it.language.name == languageName
                    }?.name ?: version.name
                    val localizedGeneration = generation.names.find {
                        it.language.name == languageName
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