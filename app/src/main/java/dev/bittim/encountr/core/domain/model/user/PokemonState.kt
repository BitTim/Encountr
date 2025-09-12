/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   12.09.25, 16:34
 */

package dev.bittim.encountr.core.domain.model.user

data class PokemonState(
    val id: Int,
    val caught: Boolean,
    val shiny: Boolean,
)
