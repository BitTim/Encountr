/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SelectLanguageState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   13.11.25, 16:07
 */

package dev.bittim.encountr.onboarding.ui.screens.selectLanguage

data class SelectLanguageState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val languageIds: List<Int> = emptyList(),
)
