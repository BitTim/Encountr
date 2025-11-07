/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       CreateSaveState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.onboarding.ui.screens.createSave

import dev.bittim.encountr.core.domain.model.api.version.Version

data class CreateSaveState(
    val languageId: Int? = null,
    val generations: Int? = null,
    val versions: List<Version?> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
