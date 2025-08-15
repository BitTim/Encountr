/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonTeamCrossRef.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 14:16
 */

package dev.bittim.encountr.core.data.user.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import kotlin.uuid.ExperimentalUuidApi

@Entity(
    tableName = "pokemon_team_ref",
    primaryKeys = ["save", "team", "pokemon"],
    indices = [
        Index(value = ["save", "team", "pokemon"]),
    ],
    foreignKeys = [
        ForeignKey(
            entity = TeamEntity::class,
            parentColumns = ["id"],
            childColumns = ["team"],
            onDelete = ForeignKey.Companion.CASCADE,
            onUpdate = ForeignKey.Companion.CASCADE
        ),
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = ["id", "save"],
            childColumns = ["pokemon", "save"],
            onDelete = ForeignKey.Companion.CASCADE,
            onUpdate = ForeignKey.Companion.CASCADE
        ),
    ]
)
data class PokemonTeamCrossRef @OptIn(ExperimentalUuidApi::class) constructor(
    val save: String,
    val team: String,
    val pokemon: Int,
)