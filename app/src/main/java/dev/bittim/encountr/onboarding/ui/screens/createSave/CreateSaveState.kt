/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       CreateSaveState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.11.25, 02:34
 */

package dev.bittim.encountr.onboarding.ui.screens.createSave

import dev.bittim.encountr.core.ui.components.version.versionCard.VersionCardState

data class CreateSaveState(
    val languageId: Int? = null,
    val generations: Int? = null,
    val versionIds: Map<Int, List<Int>> = emptyMap(),
    val versionStates: Map<Int, VersionCardState> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null
)
