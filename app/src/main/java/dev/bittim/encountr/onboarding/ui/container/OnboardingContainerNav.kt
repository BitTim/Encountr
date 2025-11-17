/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       OnboardingContainerNav.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 02:31
 */

package dev.bittim.encountr.onboarding.ui.container

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object OnboardingContainerNav

fun NavGraphBuilder.onboarding(
    navToContent: () -> Unit,
) {
    composable<OnboardingContainerNav> {
        val viewModel = koinViewModel<OnboardingContainerViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        val subNavController = rememberNavController()
        subNavController.addOnDestinationChangedListener { _, destination, _ ->
            viewModel.onDestinationChanged(destination.route ?: "")
        }

        OnboardingContainerScreen(
            state = state,
            navController = subNavController,
            navToContent = {
                viewModel.setOnboardingCompleted(true)
                navToContent()
            }
        )
    }
}

fun NavController.navToOnboarding() {
    navigate(OnboardingContainerNav) {
        popUpTo(graph.findStartDestination().id) {
            inclusive = true
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}