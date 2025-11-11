/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SelectLanguageState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.11.25, 02:34
 */

package dev.bittim.encountr.onboarding.ui.screens.selectLanguage

import dev.bittim.encountr.core.ui.components.language.languageCard.LanguageCardState

data class SelectLanguageState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val languageIds: List<Int> = emptyList(),
    val languageStates: Map<Int, LanguageCardState> = emptyMap()
)
