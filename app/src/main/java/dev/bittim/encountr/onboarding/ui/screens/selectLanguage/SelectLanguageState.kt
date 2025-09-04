/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SelectLanguageState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   04.09.25, 18:06
 */

package dev.bittim.encountr.onboarding.ui.screens.selectLanguage

import co.pokeapi.pokekotlin.model.Language

data class SelectLanguageState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val languages: List<Language>? = null
)
