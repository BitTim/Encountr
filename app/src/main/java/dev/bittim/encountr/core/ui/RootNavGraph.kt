/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       RootNavGraph.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   04.09.25, 23:26
 */

package dev.bittim.encountr.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.bittim.encountr.content.ui.container.content
import dev.bittim.encountr.content.ui.container.navToContent
import dev.bittim.encountr.onboarding.ui.container.OnboardingContainerNav
import dev.bittim.encountr.onboarding.ui.container.navToOnboarding
import dev.bittim.encountr.onboarding.ui.container.onboarding

@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = OnboardingContainerNav,
    ) {
        onboarding(navToContent = { navController.navToContent() })
        content(navToOnboarding = { navController.navToOnboarding() })
    }
}