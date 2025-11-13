/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       CreateSaveState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   13.11.25, 16:23
 */

package dev.bittim.encountr.onboarding.ui.screens.createSave

data class CreateSaveState(
    val languageId: Int? = null,
    val generations: Int? = null,
    val versionIds: List<Int> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
