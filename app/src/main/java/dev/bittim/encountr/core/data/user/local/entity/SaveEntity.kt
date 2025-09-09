/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SaveEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.09.25, 00:07
 */

package dev.bittim.encountr.core.data.user.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.domain.model.user.Save
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(
    tableName = "saves",
    indices = [
        Index(value = ["id"]),
        Index(value = ["game"]),
    ]
)
data class SaveEntity @OptIn(ExperimentalUuidApi::class) constructor(
    @PrimaryKey val id: String,
    val name: String,
    val game: Int,
) {
    @OptIn(ExperimentalUuidApi::class)
    fun toModel(): Save {
        return Save(
            id = Uuid.parse(id),
            name = name,
            version = game,
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    constructor(save: Save) : this(
        id = save.id.toString(),
        name = save.name,
        game = save.version,
    )
}