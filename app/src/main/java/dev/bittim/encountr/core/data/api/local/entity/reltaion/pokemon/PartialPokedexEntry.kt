/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PartialPokedexEntry.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.pokemon

import dev.bittim.encountr.core.domain.model.api.Handle
import dev.bittim.encountr.core.domain.model.api.pokemon.PokedexEntry

data class PartialPokedexEntry(
    val pokedexId: Int,
    val entryNumber: Int,
) {
    fun toModel(): PokedexEntry = PokedexEntry(
        pokedex = Handle(pokedexId),
        entryNumber = entryNumber
    )
}
