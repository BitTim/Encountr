/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ContentContainerViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.09.25, 19:22
 */

package dev.bittim.encountr.content.ui.container

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bittim.encountr.core.data.config.ConfigStateHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

class ContentContainerViewModel(
    private val configStateHolder: ConfigStateHolder,
) : ViewModel() {
    private var _state = MutableStateFlow(ContentContainerState())
    val state = _state.asStateFlow()

    @OptIn(ExperimentalUuidApi::class)
    fun checkOnboarded(
        navToOnboarding: () -> Unit,
    ) {
        viewModelScope.launch {
            Log.d("checkOnboarded", "checkOnboarded")
            configStateHolder.getOnboardingCompleted().filterNotNull()
                .collect { onboardingCompleted ->
                    Log.d("checkOnboarded", "onboardingCompleted: $onboardingCompleted")
                    if (!onboardingCompleted) navToOnboarding()
                }
        }
    }
}