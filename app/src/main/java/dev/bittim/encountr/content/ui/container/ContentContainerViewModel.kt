/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ContentContainerViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 02:31
 */

package dev.bittim.encountr.content.ui.container

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bittim.encountr.core.domain.useCase.config.ObserveCurrentVersion
import dev.bittim.encountr.core.domain.useCase.config.ObserveIsOnboarded
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

class ContentContainerViewModel(
    private val observeIsOnboarded: ObserveIsOnboarded,
    private val observeCurrentVersion: ObserveCurrentVersion
) : ViewModel() {
    private var _state = MutableStateFlow(ContentContainerState())
    val state = _state.asStateFlow()

    var checkOnboardedJob: Job? = null
    var fetchVersionJob: Job? = null

    @OptIn(ExperimentalUuidApi::class)
    fun checkOnboarded(
        navToOnboarding: () -> Unit,
    ) {
        checkOnboardedJob?.cancel()
        checkOnboardedJob = viewModelScope.launch {
            observeIsOnboarded().collectLatest { isOnboarded ->
                Log.d("checkOnboarded", "isOnboarded: $isOnboarded")
                if (isOnboarded != null && !isOnboarded) navToOnboarding()
            }
        }

        fetchVersionJob?.cancel()
        fetchVersionJob = viewModelScope.launch {
            observeCurrentVersion().collectLatest { version ->
                _state.update { it.copy(version = version) }
            }
        }
    }
}