/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       OnboardingContainerScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   04.09.25, 23:26
 */

package dev.bittim.encountr.onboarding.ui.container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
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
import androidx.compose.ui.platform.LocalLayoutDirection
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
import dev.bittim.encountr.onboarding.ui.screens.selectLanguage.navToOnboardingSetLanguage
import dev.bittim.encountr.onboarding.ui.screens.selectLanguage.selectLanguageScreen

@Composable
fun OnboardingContainerScreen(
    state: OnboardingContainerState,
    navController: NavHostController,
    navToContent: () -> Unit,
) {
    val layoutDirection = LocalLayoutDirection.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeContent
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(
                    PaddingValues(
                        top = it.calculateTopPadding(),
                        bottom = it.calculateBottomPadding()
                    )
                )
                .padding(top = it.calculateTopPadding(), bottom = it.calculateBottomPadding()),
            verticalArrangement = Arrangement.spacedBy(Spacing.xl),
        ) {
            OnboardingHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = it.calculateStartPadding(layoutDirection),
                        end = it.calculateEndPadding(layoutDirection)
                    ),
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
                landingScreen(navNext = navController::navToOnboardingSetLanguage)
                selectLanguageScreen(
                    navBack = { navController.navigateUp() },
                    navNext = navController::navToOnboardingCreateSave
                )
                createSaveScreen(
                    navBack = { navController.navigateUp() },
                    navNext = navToContent
                )
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
                navController = rememberNavController(),
                navToContent = {}
            )
        }
    }
}