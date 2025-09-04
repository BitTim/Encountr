/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ContentNavItem.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   04.09.25, 23:26
 */

package dev.bittim.encountr.content.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CatchingPokemon
import androidx.compose.material.icons.outlined.CatchingPokemon
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import dev.bittim.encountr.R
import dev.bittim.encountr.content.ui.screens.pokemon.list.navToPokemonListScreen
import dev.bittim.encountr.core.ui.util.UiText

enum class ContentNavItem(
    val title: UiText,
    val activeIcon: @Composable () -> Unit,
    val inactiveIcon: @Composable () -> Unit,
    val navigation: (NavController) -> Unit,
) {
    Pokemon(
        title = UiText.StringResource(R.string.nav_destination_pokemon),
        activeIcon = {
            Icon(
                imageVector = Icons.Filled.CatchingPokemon,
                contentDescription = UiText.StringResource(R.string.nav_destination_pokemon)
                    .asString()
            )
        },
        inactiveIcon = {
            Icon(
                imageVector = Icons.Outlined.CatchingPokemon,
                contentDescription = UiText.StringResource(R.string.nav_destination_pokemon)
                    .asString()
            )
        },
        navigation = NavController::navToPokemonListScreen
    ),
}