/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       OnboardingContainerViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 02:31
 */

package dev.bittim.encountr.onboarding.ui.container

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.bittim.encountr.core.data.config.ConfigStateHolder
import dev.bittim.encountr.onboarding.ui.screens.OnboardingScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingContainerViewModel(
    private val configStateHolder: ConfigStateHolder
) : ViewModel() {
    private val _state = MutableStateFlow(OnboardingContainerState())
    val state = _state.asStateFlow()

    fun onDestinationChanged(route: String) {
        val screen = OnboardingScreen.entries.find { route.contains(it.route) }
        _state.update {
            it.copy(
                route = route,
                title = screen?.title,
                description = screen?.description,
                icon = screen?.icon,
                progress = screen?.step?.toFloat()?.div(OnboardingScreen.getMaxStep())
            )
        }
    }

    fun setOnboardingCompleted(onboardingCompleted: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            configStateHolder.setOnboardingCompleted(onboardingCompleted)
        }
    }
}