/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       OnboardingContainerViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   13.08.25, 04:24
 */

package dev.bittim.encountr.onboarding.ui.container

import androidx.lifecycle.ViewModel
import dev.bittim.encountr.onboarding.ui.screens.OnboardingScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OnboardingContainerViewModel : ViewModel() {
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
}