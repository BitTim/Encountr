/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       CreateSaveViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   20.09.25, 01:24
 */

package dev.bittim.encountr.onboarding.ui.screens.createSave

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.config.ConfigStateHolder
import dev.bittim.encountr.core.data.pokeapi.repo.VersionRepository
import dev.bittim.encountr.core.data.user.repo.SaveRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

class CreateSaveViewModel(
    private val pokeApi: PokeApi,
    private val configStateHolder: ConfigStateHolder,
    private val versionRepository: VersionRepository,
    private val saveRepository: SaveRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CreateSaveState())
    val state = _state.asStateFlow()

    var fetchGamesByGenJob: Job? = null

    init {
        viewModelScope.launch {
            configStateHolder.rawState.collect { config ->
                _state.update {
                    it.copy(languageName = config?.languageName)
                }
            }
        }

        viewModelScope.launch {
            val generationCount = pokeApi.getGenerationList(0, 1).count
            _state.update { it.copy(generations = generationCount) }

            onGenChanged(1)
        }
    }

    fun onGenChanged(generationId: Int) {
        fetchGamesByGenJob?.cancel()
        fetchGamesByGenJob = viewModelScope.launch {
            _state.update { it.copy(versions = null) }

            val versions = versionRepository.getByGeneration(generationId)

            _state.update { it.copy(versions = versions) }
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