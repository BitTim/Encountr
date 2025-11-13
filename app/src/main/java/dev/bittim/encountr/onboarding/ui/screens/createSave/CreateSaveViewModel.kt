/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       CreateSaveViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   13.11.25, 16:54
 */

package dev.bittim.encountr.onboarding.ui.screens.createSave

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.config.ConfigStateHolder
import dev.bittim.encountr.core.data.user.repo.SaveRepository
import dev.bittim.encountr.core.domain.useCase.api.ObserveVersionIdsByGeneration
import dev.bittim.encountr.core.domain.useCase.ui.ObserveVersionCardState
import dev.bittim.encountr.core.ui.components.version.versionCard.VersionCardState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

class CreateSaveViewModel(
    private val pokeApi: PokeApi,
    private val configStateHolder: ConfigStateHolder,
    private val observeVersionIdsByGeneration: ObserveVersionIdsByGeneration,
    private val observeVersionCardState: ObserveVersionCardState,
    private val saveRepository: SaveRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CreateSaveState())
    val state = _state.asStateFlow()

    var versionIdsJob: Job? = null

    init {
        viewModelScope.launch {
            val generationCount = pokeApi.getGenerationList(0, 1).count
            _state.update { it.copy(generations = generationCount) }

            onGenChanged(1)
        }
    }

    fun onGenChanged(generationId: Int) {
        _state.update { it.copy(versionIds = emptyList()) }

        versionIdsJob?.cancel()
        versionIdsJob = viewModelScope.launch {
            observeVersionIdsByGeneration(generationId)
                .collectLatest { versionIds ->
                    _state.update { it.copy(versionIds = versionIds) }
                }
        }
    }

    fun observeVersion(versionId: Int?): Flow<VersionCardState?> {
        return versionId?.let { observeVersionCardState(versionId) } ?: flowOf(null)
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