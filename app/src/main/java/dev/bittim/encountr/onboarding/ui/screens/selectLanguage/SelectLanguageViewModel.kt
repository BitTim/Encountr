/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SelectLanguageViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   13.11.25, 16:55
 */

package dev.bittim.encountr.onboarding.ui.screens.selectLanguage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bittim.encountr.core.data.api.repo.language.LanguageRepository
import dev.bittim.encountr.core.data.config.ConfigStateHolder
import dev.bittim.encountr.core.domain.useCase.ui.ObserveLanguageCardState
import dev.bittim.encountr.core.ui.components.language.languageCard.LanguageCardState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SelectLanguageViewModel(
    private val configStateHolder: ConfigStateHolder,
    private val languageRepository: LanguageRepository,
    private val observeLanguageCardState: ObserveLanguageCardState,
) : ViewModel() {
    private val _state = MutableStateFlow(SelectLanguageState())
    val state = _state.asStateFlow()

    private var fetchJob: Job? = null

    init {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                languageRepository.getIds()
                    .stateIn(viewModelScope, WhileSubscribed(5000), emptyList())
                    .collectLatest { languageIds ->
                        _state.update { it.copy(languageIds = languageIds) }
                    }
            }
        }
    }

    fun observeLanguage(languageId: Int?): Flow<LanguageCardState?> {
        return languageId?.let { observeLanguageCardState(languageId) } ?: flowOf(null)
    }

    fun onContinue(languageId: Int, navNext: () -> Unit) {
        viewModelScope.launch {
            configStateHolder.setLanguageId(languageId)
            launch(Dispatchers.Main) { navNext() }
        }
    }
}