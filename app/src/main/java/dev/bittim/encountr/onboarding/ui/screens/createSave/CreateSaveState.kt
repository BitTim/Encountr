/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       CreateSaveState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.09.25, 18:03
 */

package dev.bittim.encountr.onboarding.ui.screens.createSave

import dev.bittim.encountr.core.domain.model.pokeapi.Version

data class CreateSaveState(
    val languageName: String? = null,
    val generations: Int? = null,
    val versions: List<Version>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
