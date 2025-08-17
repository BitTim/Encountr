/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       CreateSaveState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.08.25, 21:02
 */

package dev.bittim.encountr.onboarding.ui.screens.createSave

import dev.bittim.encountr.core.ui.components.GameCardState

data class Game(
    val id: Int,
    val localizedName: String,
    val localizedGeneration: String,
    val imageUrl: String?
) {
    fun toGameSelectorState(): GameCardState {
        return GameCardState(
            name = localizedName,
            generation = localizedGeneration,
            imageUrl = imageUrl
        )
    }
}

data class CreateSaveState(
    val games: List<Game>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
