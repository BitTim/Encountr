/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonStateEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.user.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import dev.bittim.encountr.core.domain.model.user.PokemonState
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(
    tableName = "pokemon_state",
    indices = [
        Index(value = ["id", "save"]),
    ],
    primaryKeys = ["id", "save"],
    foreignKeys = [
        ForeignKey(
            entity = SaveEntity::class,
            parentColumns = ["id"],
            childColumns = ["save"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class PokemonStateEntity @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Int,
    val save: String,
    val caught: Boolean,
    val shiny: Boolean,
) {
    fun toModel(): PokemonState {
        return PokemonState(
            id = id,
            caught = caught,
            shiny = shiny
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    constructor(pokemonState: PokemonState, save: Uuid) : this(
        id = pokemonState.id,
        save = save.toString(),
        caught = pokemonState.caught,
        shiny = pokemonState.shiny
    )
}
