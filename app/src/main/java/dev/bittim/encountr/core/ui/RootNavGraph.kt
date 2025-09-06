/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       RootNavGraph.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   06.09.25, 02:27
 */

package dev.bittim.encountr.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.bittim.encountr.content.ui.container.ContentContainerNav
import dev.bittim.encountr.content.ui.container.content
import dev.bittim.encountr.content.ui.container.navToContent
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
        startDestination = ContentContainerNav,
    ) {
        onboarding(navToContent = { navController.navToContent() })
        content(navToOnboarding = { navController.navToOnboarding() })
    }
}