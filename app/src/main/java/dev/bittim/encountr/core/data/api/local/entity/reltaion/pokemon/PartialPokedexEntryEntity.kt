/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PartialPokedexEntryEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.pokemon

import dev.bittim.encountr.core.domain.model.api.pokemon.PokedexEntry

data class PartialPokedexEntryEntity(
    val pokedexId: Int,
    val entryNumber: Int,
) {
    fun toModel(): PokedexEntry = PokedexEntry(
        pokedexId = pokedexId,
        entryNumber = entryNumber
    )
}
