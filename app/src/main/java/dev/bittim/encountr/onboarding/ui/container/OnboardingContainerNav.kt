/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       OnboardingContainerNav.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   14.08.25, 03:18
 */

package dev.bittim.encountr.onboarding.ui.container

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.bittim.encountr.onboarding.ui.screens.landing.LandingNav
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object OnboardingContainerNav

fun NavGraphBuilder.onboarding() {
    composable<OnboardingContainerNav> {
        val viewModel = koinViewModel<OnboardingContainerViewModel>()
        val state = viewModel.state.collectAsStateWithLifecycle()

        val subNavController = rememberNavController()
        subNavController.addOnDestinationChangedListener { _, destination, _ ->
            viewModel.onDestinationChanged(destination.route ?: "")
        }

        OnboardingContainerScreen(
            state = state.value,
            navController = subNavController
        )
    }
}

fun NavController.navToOnboarding() {
    navigate(LandingNav) {
        popUpTo(graph.findStartDestination().id) {
            inclusive = true
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}