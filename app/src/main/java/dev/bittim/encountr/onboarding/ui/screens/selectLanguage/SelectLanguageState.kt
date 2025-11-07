/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SelectLanguageState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.onboarding.ui.screens.selectLanguage

import dev.bittim.encountr.core.domain.model.api.language.Language

data class SelectLanguageState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val languages: List<Language?> = emptyList()
)
