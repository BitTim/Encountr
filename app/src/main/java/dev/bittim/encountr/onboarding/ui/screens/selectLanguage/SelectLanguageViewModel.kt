/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SelectLanguageViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   04.09.25, 18:06
 */

package dev.bittim.encountr.onboarding.ui.screens.selectLanguage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.config.ConfigStateHolder
import dev.bittim.encountr.core.data.pokeapi.extension.hasSelfLocalizedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SelectLanguageViewModel(
    private val configStateHolder: ConfigStateHolder
) : ViewModel() {
    private val _state = MutableStateFlow(SelectLanguageState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val count = PokeApi.getLanguageList(0, 1).count

            val response = PokeApi.getLanguageList(0, count)
            val languages = response.results.mapNotNull { handle ->
                PokeApi.getLanguage(handle.id).takeIf { it.hasSelfLocalizedName() }
            }

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