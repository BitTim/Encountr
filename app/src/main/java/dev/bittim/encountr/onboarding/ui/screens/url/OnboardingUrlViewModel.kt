/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       OnboardingUrlViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   12.08.25, 01:11
 */

package dev.bittim.encountr.onboarding.ui.screens.url

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bittim.encountr.R
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

class OnboardingUrlViewModel(
    private val definitionRepository: DefinitionRepository
) : ViewModel() {
    private val _state = MutableStateFlow(OnboardingUrlState())
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
                        navNext()
                    }
                }
            }
        }
    }
}