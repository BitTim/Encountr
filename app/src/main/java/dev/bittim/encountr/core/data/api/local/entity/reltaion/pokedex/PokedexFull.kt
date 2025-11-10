/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexFull.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.pokedex

import androidx.room.Embedded
import androidx.room.Relation
import dev.bittim.encountr.core.data.api.local.entity.base.TimestampedEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.junction.PokedexPokemonJunction

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
) : TimestampedEntity by pokedex {
    fun toModel() = pokedex.toModel(localizedNames.map { it.toModel() }, pokemonIds)
}
