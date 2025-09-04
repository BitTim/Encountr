/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ContentModule.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   04.09.25, 23:26
 */

package dev.bittim.encountr.content.di

import dev.bittim.encountr.content.ui.container.ContentContainerViewModel
import dev.bittim.encountr.content.ui.screens.pokemon.list.PokemonListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val contentModule = module {
    // region:      -- ViewModels

    viewModelOf(::ContentContainerViewModel)
    viewModelOf(::PokemonListViewModel)

    // endregion:   -- ViewModels
}