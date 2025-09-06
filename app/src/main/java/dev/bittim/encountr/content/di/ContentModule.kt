/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ContentModule.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.09.25, 01:07
 */

package dev.bittim.encountr.content.di

import dev.bittim.encountr.content.ui.container.ContentContainerViewModel
import dev.bittim.encountr.content.ui.screens.locations.list.LocationListViewModel
import dev.bittim.encountr.content.ui.screens.pokemon.list.PokemonListViewModel
import dev.bittim.encountr.content.ui.screens.teams.list.TeamListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val contentModule = module {
    // region:      -- ViewModels

    viewModelOf(::ContentContainerViewModel)

    viewModelOf(::PokemonListViewModel)
    viewModelOf(::LocationListViewModel)
    viewModelOf(::TeamListViewModel)

    // endregion:   -- ViewModels
}