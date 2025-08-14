/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LandingNav.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   14.08.25, 03:18
 */

package dev.bittim.encountr.onboarding.ui.screens.landing

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object LandingNav

fun NavGraphBuilder.landingScreen(
    navNext: () -> Unit
) {
    composable<LandingNav> {
        val viewModel = koinViewModel<LandingViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        LandingScreen(
            state = state,
            resetError = viewModel::resetError,
            onContinue = viewModel::onContinue,
            navNext = navNext
        )
    }
}

fun NavController.navToOnboardingLanding() {
    navigate(LandingNav) {
        launchSingleTop = true
        restoreState = true
    }
}