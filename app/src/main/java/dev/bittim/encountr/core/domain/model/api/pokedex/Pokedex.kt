/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Pokedex.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.domain.model.api.pokedex

import dev.bittim.encountr.core.domain.model.api.language.LocalizedString

data class Pokedex(
    val id: Int,
    val name: String,
    val isMainSeries: Boolean,
    val localizedNames: List<LocalizedString>,
    val pokemonIds: List<Int>,
)
