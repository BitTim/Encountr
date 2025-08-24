/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SelectLocaleState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   24.08.25, 20:06
 */

package dev.bittim.encountr.onboarding.ui.screens.selectLocale

import co.pokeapi.pokekotlin.model.Language

data class SelectLocaleState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val locales: List<Language>? = null
)
