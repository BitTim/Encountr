/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ContentContainerNav.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   06.09.25, 02:27
 */

package dev.bittim.encountr.content.ui.container

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
object ContentContainerNav

fun NavGraphBuilder.content(
    navToOnboarding: () -> Unit,
) {
    composable<ContentContainerNav> {
        val viewModel: ContentContainerViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        viewModel.checkOnboarded(navToOnboarding = navToOnboarding)

        val subNavController = rememberNavController()

        ContentContainerScreen(
            state = state,
            navController = subNavController,
        )
    }
}

fun NavController.navToContent() {
    navigate(ContentContainerNav) {
        popUpTo(graph.findStartDestination().id) {
            inclusive = true
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}