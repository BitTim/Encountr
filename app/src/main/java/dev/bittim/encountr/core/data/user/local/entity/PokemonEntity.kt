/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   12.09.25, 16:34
 */

package dev.bittim.encountr.core.data.user.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import dev.bittim.encountr.core.domain.model.user.PokemonState
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(
    tableName = "pokemon",
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
data class PokemonEntity @OptIn(ExperimentalUuidApi::class) constructor(
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
