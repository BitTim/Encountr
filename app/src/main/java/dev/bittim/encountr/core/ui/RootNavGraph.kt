/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       RootNavGraph.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   12.08.25, 01:10
 */

package dev.bittim.encountr.core.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.bittim.encountr.onboarding.ui.screens.url.OnboardingUrlNav
import dev.bittim.encountr.onboarding.ui.screens.url.onboardingUrl

@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = OnboardingUrlNav,
    ) {
        onboardingUrl({ Log.d("RootNavGraph", "navToNext") })
    }
}