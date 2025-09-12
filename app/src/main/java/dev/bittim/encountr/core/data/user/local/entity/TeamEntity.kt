/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TeamEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   12.09.25, 16:34
 */

package dev.bittim.encountr.core.data.user.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.domain.model.user.Team
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(
    tableName = "teams",
    indices = [
        Index(value = ["id", "save"]),
    ],
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
data class TeamEntity @OptIn(ExperimentalUuidApi::class) constructor(
    @PrimaryKey val id: String,
    val save: String,
    val name: String,
) {
    @OptIn(ExperimentalUuidApi::class)
    fun toModel(): Team {
        return Team(
            id = Uuid.parse(id),
            name = name,
            pokemonStates = emptyList(),
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    constructor(team: Team, save: Uuid) : this(
        id = team.id.toString(),
        save = save.toString(),
        name = team.name
    )
}