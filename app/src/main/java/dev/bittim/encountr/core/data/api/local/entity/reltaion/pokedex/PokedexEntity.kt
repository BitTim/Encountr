/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.11.25, 03:07
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.pokedex

import androidx.room.Embedded
import androidx.room.Relation
import dev.bittim.encountr.core.data.api.local.entity.base.CombinedEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexStub
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString

data class PokedexEntity(
    @Embedded override val stub: PokedexStub,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    ) override val detail: PokedexDetailEntity?
) : CombinedEntity {
    fun toModel(
        localizedNames: List<LocalizedString>,
        pokemonIds: List<Int>
    ) = detail?.toModel(
        localizedNames = localizedNames,
        pokemonIds = pokemonIds
    )
}
