/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexFull.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.pokedex

import androidx.room.Embedded
import androidx.room.Relation
import dev.bittim.encountr.core.data.api.local.entity.base.ExpirableEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.junction.PokedexPokemonJunction
import dev.bittim.encountr.core.domain.model.api.Handle

data class PokedexFull(
    @Embedded val pokedex: PokedexEntity,
    @Relation(
        entity = PokedexLocalizedNameEntity::class,
        parentColumn = "id",
        entityColumn = "pokedexId"
    ) val localizedNames: List<PokedexLocalizedNameEntity>,
    @Relation(
        entity = PokedexPokemonJunction::class,
        parentColumn = "id",
        entityColumn = "pokedexId",
        projection = ["pokemonId"]
    ) val pokemonIds: List<Int>
) : ExpirableEntity by pokedex {
    fun toModel() = pokedex.toModel(
        localizedNames.map { it.toModel() },
        pokemonIds.map { Handle(it) }
    )
}
