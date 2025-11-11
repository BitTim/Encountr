/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       CreateSaveViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.11.25, 02:34
 */

package dev.bittim.encountr.onboarding.ui.screens.createSave

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.config.ConfigStateHolder
import dev.bittim.encountr.core.data.user.repo.SaveRepository
import dev.bittim.encountr.core.domain.useCase.api.GetVersionsByGeneration
import dev.bittim.encountr.core.domain.useCase.ui.ObserveVersionCardState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import kotlin.uuid.ExperimentalUuidApi

class CreateSaveViewModel(
    private val pokeApi: PokeApi,
    private val configStateHolder: ConfigStateHolder,
    private val getVersionsByGeneration: GetVersionsByGeneration,
    private val observeVersionCardState: ObserveVersionCardState,
    private val saveRepository: SaveRepository
) : ViewModel() {
    private val _state = MutableStateFlow(CreateSaveState())
    val state = _state.asStateFlow()

    private val versionIdsJob = ConcurrentHashMap<Int, Job>()
    private val versionStateJobs = ConcurrentHashMap<Int, Job>()

    init {
        viewModelScope.launch {
            val generationCount = pokeApi.getGenerationList(0, 1).count
            _state.update { it.copy(generations = generationCount) }

            onGenChanged(1)
        }
    }

    fun onGenChanged(generationId: Int) {
        if (versionIdsJob[generationId] != null) return

        val job = viewModelScope.launch(Dispatchers.IO) {
            versionIdsJob[generationId] = this.coroutineContext[Job]!!

            try {
                getVersionsByGeneration(generationId)
                    .collectLatest { versionIds ->
                        if (versionIds.isEmpty()) return@collectLatest
                        _state.update { it.copy(versionIds = it.versionIds + (generationId to versionIds)) }
                    }
            } catch (_: Exception) {
                _state.update { it.copy(versionIds = it.versionIds - generationId) }
            } finally {
                versionIdsJob.remove(generationId)
            }
        }

        versionIdsJob[generationId] = job
    }

    fun observeVersion(versionId: Int) {
        if (versionStateJobs[versionId] != null) return

        val job = viewModelScope.launch(Dispatchers.IO) {
            versionStateJobs[versionId] = this.coroutineContext[Job]!!

            try {
                observeVersionCardState(versionId)
                    .collectLatest { versionCardState ->
                        if (versionCardState == null) return@collectLatest
                        _state.update { it.copy(versionStates = it.versionStates + (versionId to versionCardState)) }
                    }
            } catch (_: Exception) {
                _state.update { it.copy(versionStates = it.versionStates - versionId) }
            } finally {
                versionStateJobs.remove(versionId)
            }
        }

        versionStateJobs[versionId] = job
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