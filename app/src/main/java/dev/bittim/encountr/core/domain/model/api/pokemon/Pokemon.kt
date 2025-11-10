/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Pokemon.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.domain.model.api.pokemon

import dev.bittim.encountr.core.domain.model.api.language.LocalizedString

data class Pokemon(
    val id: Int,
    val entryNumbers: List<PokedexEntry>,
    val name: String,
    val localizedNames: List<LocalizedString>,
    val height: String,
    val weight: String,
    val sprites: List<PokemonSprites>,
    val typeIds: List<Int>,
)
