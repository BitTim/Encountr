/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LandingViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.09.25, 00:07
 */

package dev.bittim.encountr.onboarding.ui.screens.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.R
import dev.bittim.encountr.core.data.config.ConfigStateHolder
import dev.bittim.encountr.core.data.defs.DefinitionsError
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepository
import dev.bittim.encountr.core.di.Constants
import dev.bittim.encountr.core.domain.error.Result
import dev.bittim.encountr.core.ui.util.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LandingViewModel(
    private val configStateHolder: ConfigStateHolder,
    private val definitionRepository: DefinitionRepository
) : ViewModel() {
    private val _state = MutableStateFlow(LandingState())
    val state = _state.asStateFlow()

    init {
        val url = configStateHolder.state.value?.definitionsUrl ?: Constants.DEFAULT_DEFS_URL
        checkUrl(url)
    }

    fun checkUrl(urlString: String) {
        _state.update { it.copy(fetching = true) }

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = definitionRepository.fetchDefinition(urlString)
                when (result) {
                    is Result.Err -> {
                        val error = when (result.error) {
                            DefinitionsError.InvalidUrl -> UiText.StringResource(R.string.error_invalid_url)
                            DefinitionsError.InvalidResponse -> UiText.StringResource(R.string.error_invalid_response)
                            DefinitionsError.InvalidContent -> UiText.StringResource(R.string.error_invalid_content)
                            DefinitionsError.Cache -> UiText.StringResource(R.string.error_cache)
                            else -> UiText.StringResource(R.string.error_unknown)
                        }
                        _state.update { it.copy(fetching = false, urlError = error) }
                    }

                    is Result.Ok -> {
                        val imageUrl =
                            PokeApi.getPokemonVariety(definitionRepository.getDefinitionIconPokemon()).sprites.frontDefault

                        _state.update {
                            it.copy(
                                imageUrl = imageUrl,
                                lastValidUrl = urlString,
                                fetching = false,
                                urlError = null
                            )
                        }
                    }
                }
            }
        }
    }

    fun resetError() {
        _state.update { it.copy(urlError = null) }
    }

    fun onContinue(
        urlString: String,
        navNext: () -> Unit
    ) {
        val lastValidUrl = _state.value.lastValidUrl
        if (urlString != lastValidUrl) return

        viewModelScope.launch {
            configStateHolder.setDefinitionsUrl(urlString)
            launch(Dispatchers.Main) { navNext() }
        }
    }
}