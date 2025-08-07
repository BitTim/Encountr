/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       AppModule.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.08.25, 02:32
 */

package dev.bittim.encountr.core.di

import dev.bittim.encountr.content.pokemon.ui.screen.details.PokemonDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::PokemonDetailsViewModel)
}