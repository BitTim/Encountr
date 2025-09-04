/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonListNav.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   04.09.25, 23:26
 */

package dev.bittim.encountr.content.ui.screens.pokemon.list

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.bittim.encountr.onboarding.ui.screens.createSave.CreateSaveNav
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object PokemonListNav

fun NavGraphBuilder.pokemonListScreen() {
    composable<PokemonListNav> {
        val viewModel = koinViewModel<PokemonListViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        PokemonListScreen(state = state)
    }
}

fun NavController.navToPokemonListScreen() {
    navigate(CreateSaveNav) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }
}