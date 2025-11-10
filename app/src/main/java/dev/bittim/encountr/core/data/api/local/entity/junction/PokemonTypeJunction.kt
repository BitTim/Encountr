/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonTypeJunction.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.entity.junction

import androidx.room.Entity
import androidx.room.ForeignKey
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonStub
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeStub

@Entity(
    tableName = "pokemon_type_junction",
    primaryKeys = ["pokemonId", "typeId"],
    foreignKeys = [
        ForeignKey(
            entity = PokemonStub::class,
            parentColumns = ["id"],
            childColumns = ["pokemonId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TypeStub::class,
            parentColumns = ["id"],
            childColumns = ["typeId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
    ]
)
data class PokemonTypeJunction(
    val pokemonId: Int,
    val typeId: Int,
)