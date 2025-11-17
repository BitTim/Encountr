/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Pokemon.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 19:43
 */

package dev.bittim.encountr.core.domain.model.api.pokemon

import dev.bittim.encountr.core.domain.model.api.language.LocalizedString

data class Pokemon(
    val id: Int,
    val entryNumbers: Map<Int, Int>,
    val name: String,
    val localizedNames: List<LocalizedString>,
    val height: String,
    val weight: String,
    val sprites: List<PokemonSprites>,
    val typeIds: List<Int>,
)
