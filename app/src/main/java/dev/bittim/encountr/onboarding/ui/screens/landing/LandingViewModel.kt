/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LandingViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   04.09.25, 18:06
 */

package dev.bittim.encountr.onboarding.ui.screens.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bittim.encountr.R
import dev.bittim.encountr.core.data.config.ConfigStateHolder
import dev.bittim.encountr.core.data.defs.DefinitionsError
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepository
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

    fun resetError() {
        _state.update { it.copy(urlError = null) }
    }

    fun onContinue(
        urlString: String,
        navNext: () -> Unit
    ) {
        _state.update { it.copy(fetching = true) }

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = definitionRepository.fetchDefinitions(urlString)
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
                        configStateHolder.setDefinitionsUrl(urlString)
                        _state.update { it.copy(fetching = false, urlError = null) }
                        launch(Dispatchers.Main) { navNext() }
                    }
                }
            }
        }
    }
}