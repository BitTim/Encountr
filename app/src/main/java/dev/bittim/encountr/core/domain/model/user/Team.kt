/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Team.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   12.09.25, 16:34
 */

package dev.bittim.encountr.core.domain.model.user

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Team @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid,
    val name: String,
    val pokemonStates: List<PokemonState>,
)
