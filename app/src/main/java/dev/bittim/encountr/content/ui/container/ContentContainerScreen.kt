/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ContentContainerScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 02:31
 */

package dev.bittim.encountr.content.ui.container

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.bittim.encountr.content.ui.screens.ContentNavItem
import dev.bittim.encountr.content.ui.screens.locations.list.locationListScreen
import dev.bittim.encountr.content.ui.screens.pokemon.list.PokemonListNav
import dev.bittim.encountr.content.ui.screens.pokemon.list.pokemonListScreen
import dev.bittim.encountr.content.ui.screens.teams.list.teamListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentContainerScreen(
    state: ContentContainerState,
    navController: NavHostController,
) {
    var currentDestination by rememberSaveable { mutableStateOf(ContentNavItem.Pokemon) }

    NavigationSuiteScaffold(
        modifier = Modifier.fillMaxSize(),
        navigationSuiteItems = {
            ContentNavItem.entries.forEach {
                val isSelected = it == currentDestination

                item(
                    icon = if (isSelected) it.activeIcon else it.inactiveIcon,
                    label = { Text(it.title.asString()) },
                    selected = isSelected,
                    onClick = {
                        currentDestination = it
                        it.navigation(navController)
                    })
            }
        }
    ) {
        NavHost(
            modifier = Modifier
                .fillMaxSize(),
            navController = navController,
            startDestination = PokemonListNav
        ) {
            pokemonListScreen(version = state.version)
            locationListScreen()
            teamListScreen()
        }
    }
}