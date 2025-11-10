/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.pokedex

import androidx.room.Embedded
import androidx.room.Relation
import dev.bittim.encountr.core.data.api.local.entity.base.TimestampedEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexStub
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString

data class PokedexEntity(
    @Embedded val stub: PokedexStub,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    ) val detail: PokedexDetailEntity
) : TimestampedEntity by stub {
    fun toModel(
        localizedNames: List<LocalizedString>,
        pokemonIds: List<Int>
    ) = detail.toModel(
        localizedNames = localizedNames,
        pokemonIds = pokemonIds
    )
}
