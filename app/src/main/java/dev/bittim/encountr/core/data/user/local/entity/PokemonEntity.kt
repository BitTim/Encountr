/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 11:01
 */

package dev.bittim.encountr.core.data.user.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import dev.bittim.encountr.core.domain.model.user.Pokemon
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
    val save: Uuid,
    val caught: Boolean,
) {
    fun toModel(): Pokemon {
        return Pokemon(
            id = id,
            caught = caught,
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    constructor(pokemon: Pokemon, save: Uuid) : this(
        id = pokemon.id,
        save = save,
        caught = pokemon.caught
    )
}
