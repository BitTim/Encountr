/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SelectLanguageViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:37
 */

package dev.bittim.encountr.onboarding.ui.screens.selectLanguage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bittim.encountr.core.data.api.repo.language.LanguageRepository
import dev.bittim.encountr.core.data.config.ConfigStateHolder
import dev.bittim.encountr.core.domain.useCase.ui.ObserveLanguageCardState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap

class SelectLanguageViewModel(
    private val configStateHolder: ConfigStateHolder,
    private val languageRepository: LanguageRepository,
    private val observeLanguageCardState: ObserveLanguageCardState,
) : ViewModel() {
    private val _state = MutableStateFlow(SelectLanguageState())
    val state = _state.asStateFlow()

    private var fetchJob: Job? = null
    private val languageJobs = ConcurrentHashMap<Int, Job>()

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

    fun observeLanguage(languageId: Int) {
        if (languageJobs[languageId] != null) return

        val job = viewModelScope.launch(Dispatchers.IO) {
            languageJobs[languageId] = this.coroutineContext[Job]!!

            try {
                observeLanguageCardState(languageId).collectLatest { languageCardState ->
                    if (languageCardState == null) return@collectLatest
                    _state.update { it.copy(languages = it.languages + (languageId to languageCardState)) }
                }
            } catch (_: Exception) {
                _state.update { it.copy(languages = it.languages - languageId) }
            } finally {
                languageJobs.remove(languageId)
            }
        }

        languageJobs[languageId] = job
    }

    fun onContinue(languageId: Int, navNext: () -> Unit) {
        viewModelScope.launch {
            configStateHolder.setLanguageId(languageId)
            launch(Dispatchers.Main) { navNext() }
        }
    }
}