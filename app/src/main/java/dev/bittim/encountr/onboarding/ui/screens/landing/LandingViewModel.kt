/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LandingViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:15
 */

package dev.bittim.encountr.onboarding.ui.screens.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LandingViewModel(
    private val definitionRepository: DefinitionRepository
) : ViewModel() {
    private val _state = MutableStateFlow(LandingState())
    val state = _state.asStateFlow()

    var loadDefinitionsJob: Job? = null

    init {
        loadDefinitionsJob?.cancel()
        loadDefinitionsJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                definitionRepository.loadDefinition()
                _state.update { it.copy(imageUrl = definitionRepository.getDefinitionIcon()) }
            }
        }
    }

    fun onContinue(navNext: () -> Unit) {
        viewModelScope.launch {
            launch(Dispatchers.Main) { navNext() }
        }
    }
}