/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SelectLanguageViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.onboarding.ui.screens.selectLanguage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bittim.encountr.core.data.api.repo.language.LanguageRepository
import dev.bittim.encountr.core.data.config.ConfigStateHolder
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

class SelectLanguageViewModel(
    private val configStateHolder: ConfigStateHolder,
    private val languageRepository: LanguageRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SelectLanguageState())
    val state = _state.asStateFlow()

    private var fetchJob: Job? = null

    init {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Log.d("init", "Loading languages")
                languageRepository.get()
                    .stateIn(viewModelScope, WhileSubscribed(5000), emptyList())
                    .collectLatest { languages ->
                        Log.d("init", "languages: $languages")
                        _state.update { it.copy(languages = languages) }
                    }
            }
        }
    }

    fun onContinue(languageId: Int, navNext: () -> Unit) {
        viewModelScope.launch {
            configStateHolder.setLanguageId(languageId)
            launch(Dispatchers.Main) { navNext() }
        }
    }
}