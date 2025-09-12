/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SelectLanguageViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   12.09.25, 17:02
 */

package dev.bittim.encountr.onboarding.ui.screens.selectLanguage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bittim.encountr.core.data.config.ConfigStateHolder
import dev.bittim.encountr.core.data.pokeapi.repo.LanguageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SelectLanguageViewModel(
    private val configStateHolder: ConfigStateHolder,
    private val languageRepository: LanguageRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SelectLanguageState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            Log.d("init", "Loading languages")
            val languages = languageRepository.getAll()

            Log.d("init", "Loaded languages: ${languages.count()}")
            _state.update { it.copy(languages = languages) }
        }
    }

    fun onContinue(languageName: String, navNext: () -> Unit) {
        viewModelScope.launch {
            configStateHolder.setLanguageName(languageName)
            launch(Dispatchers.Main) { navNext() }
        }
    }
}