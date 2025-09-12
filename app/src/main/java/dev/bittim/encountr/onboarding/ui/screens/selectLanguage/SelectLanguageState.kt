/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SelectLanguageState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   12.09.25, 16:45
 */

package dev.bittim.encountr.onboarding.ui.screens.selectLanguage

import dev.bittim.encountr.core.domain.model.pokeapi.Language

data class SelectLanguageState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val languages: List<Language>? = null
)
