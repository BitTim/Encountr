/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexPokemonJunction.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 23:54
 */

package dev.bittim.encountr.core.data.api.local.entity.junction

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexStub
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonStub

@Entity(
    tableName = "pokedex_pokemon_junction",
    primaryKeys = ["pokedexId", "pokemonId"],
    foreignKeys = [
        ForeignKey(
            entity = PokedexStub::class,
            parentColumns = ["id"],
            childColumns = ["pokedexId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PokemonStub::class,
            parentColumns = ["id"],
            childColumns = ["pokemonId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
    ],
    indices = [Index(value = ["pokemonId"])]
)
data class PokedexPokemonJunction(
    val pokedexId: Int,
    val pokemonId: Int,
    val entryNumber: Int,
)