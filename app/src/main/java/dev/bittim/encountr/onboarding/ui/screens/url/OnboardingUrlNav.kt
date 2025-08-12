/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       OnboardingUrlNav.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   12.08.25, 01:11
 */

package dev.bittim.encountr.onboarding.ui.screens.url

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data object OnboardingUrlNav

fun NavGraphBuilder.onboardingUrl(
    navNext: () -> Unit
) {
    composable<OnboardingUrlNav> {
        val viewModel = koinViewModel<OnboardingUrlViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        OnboardingUrlScreen(
            state = state,
            resetError = viewModel::resetError,
            onContinue = viewModel::onContinue,
            navNext = navNext
        )
    }
}

fun NavController.navToOnboardingUrl() {
    navigate(OnboardingUrlNav) {
        popUpTo(graph.findStartDestination().id) {
            inclusive = true
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}