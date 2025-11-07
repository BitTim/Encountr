/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexEntry.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.10.25, 16:24
 */

package dev.bittim.encountr.core.domain.model.api.pokemon

import dev.bittim.encountr.core.domain.model.api.Handle
import dev.bittim.encountr.core.domain.model.api.pokedex.Pokedex

data class PokedexEntry(
    val pokedex: Handle<Pokedex>,
    val entryNumber: Int,
)
