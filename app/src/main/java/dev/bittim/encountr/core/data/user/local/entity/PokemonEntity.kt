/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   14.08.25, 22:27
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
        Index(value = ["id"]),
    ],
    primaryKeys = ["id", "saveId"],
    foreignKeys = [
        ForeignKey(
            entity = SaveEntity::class,
            parentColumns = ["id"],
            childColumns = ["saveId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class PokemonEntity @OptIn(ExperimentalUuidApi::class) constructor(
    val id: String,
    val saveId: Uuid,
    val caught: Boolean,
) {
    fun toModel(): Pokemon {
        return Pokemon(
            id = id,
            caught = caught,
        )
    }
}
