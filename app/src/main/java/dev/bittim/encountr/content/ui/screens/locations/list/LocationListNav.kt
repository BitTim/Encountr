/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LocationListNav.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.09.25, 01:07
 */

package dev.bittim.encountr.content.ui.screens.locations.list

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object LocationListNav

fun NavGraphBuilder.locationListScreen() {
    composable<LocationListNav> {
        val viewModel = koinViewModel<LocationListViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        LocationListScreen(state = state)
    }
}

fun NavController.navToLocationListScreen() {
    navigate(LocationListNav) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }
}