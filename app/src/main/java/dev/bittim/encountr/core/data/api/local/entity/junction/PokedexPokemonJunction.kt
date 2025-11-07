/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexPokemonJunction.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.entity.junction

import androidx.room.Entity
import androidx.room.ForeignKey
import dev.bittim.encountr.core.data.api.local.entity.base.ExpirableEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexEntity

@Entity(
    tableName = "pokedex_pokemon_junction",
    primaryKeys = ["pokemonId", "pokedexId"],
    foreignKeys = [
        ForeignKey(
            entity = PokedexEntity::class,
            parentColumns = ["id"],
            childColumns = ["pokedexId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
    ]
)
data class PokedexPokemonJunction(
    val pokedexId: Int,
    val pokemonId: Int,
    val entryNumber: Int,
    override val expiresAt: Long,
) : ExpirableEntity