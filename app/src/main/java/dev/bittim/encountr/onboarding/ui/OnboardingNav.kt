/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       OnboardingNav.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.08.25, 18:09
 */

package dev.bittim.encountr.onboarding.ui

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data object OnboardingNav

fun NavGraphBuilder.onboarding(
    navNext: () -> Unit
) {
    composable<OnboardingNav> {
        val viewModel = koinViewModel<OnboardingViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        OnboardingScreen(
            state = state,
            resetError = viewModel::resetError,
            onContinue = viewModel::onContinue,
            navNext = navNext
        )
    }
}

fun NavController.navToOnboarding() {
    navigate(OnboardingNav) {
        popUpTo(graph.findStartDestination().id) {
            inclusive = true
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}