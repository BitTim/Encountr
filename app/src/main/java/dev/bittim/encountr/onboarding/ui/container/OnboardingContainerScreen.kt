/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       OnboardingContainerScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   14.08.25, 03:15
 */

package dev.bittim.encountr.onboarding.ui.container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WavingHand
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.bittim.encountr.R
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.UiText
import dev.bittim.encountr.core.ui.util.annotations.ScreenPreview
import dev.bittim.encountr.onboarding.ui.components.OnboardingHeader
import dev.bittim.encountr.onboarding.ui.screens.createSave.createSaveScreen
import dev.bittim.encountr.onboarding.ui.screens.createSave.navToOnboardingCreateSave
import dev.bittim.encountr.onboarding.ui.screens.landing.LandingNav
import dev.bittim.encountr.onboarding.ui.screens.landing.landingScreen

@Composable
fun OnboardingContainerScreen(
    state: OnboardingContainerState,
    navController: NavHostController,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeContent
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(Spacing.xl),
        ) {
            OnboardingHeader(
                modifier = Modifier.fillMaxWidth(),
                title = state.title ?: UiText.DynamicString(""),
                progress = state.progress ?: 0.0f,
                description = state.description ?: UiText.DynamicString(""),
                icon = state.icon,
                maxLines = 2
            )

            NavHost(
                modifier = Modifier.weight(1f),
                navController = navController,
                startDestination = LandingNav
            ) {
                landingScreen(navNext = navController::navToOnboardingCreateSave)
                createSaveScreen(navBack = { navController.navigateUp() })
            }
        }
    }
}

@ScreenPreview
@Composable
private fun OnboardingContainerScreenPreview() {
    EncountrTheme {
        Surface {
            OnboardingContainerScreen(
                state = OnboardingContainerState(
                    title = UiText.StringResource(R.string.onboarding_landing_title),
                    description = UiText.StringResource(R.string.onboarding_landing_description),
                    icon = Icons.Default.WavingHand,
                    progress = 0.5f,
                    route = null
                ),
                navController = rememberNavController()
            )
        }
    }
}