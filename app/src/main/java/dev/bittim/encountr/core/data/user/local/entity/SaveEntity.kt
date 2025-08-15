/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SaveEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 10:50
 */

package dev.bittim.encountr.core.data.user.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.domain.model.user.Save
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity(tableName = "saves")
data class SaveEntity @OptIn(ExperimentalUuidApi::class) constructor(
    @PrimaryKey val id: Uuid,
    val name: String,
    val game: Int,
) {
    @OptIn(ExperimentalUuidApi::class)
    fun toModel(): Save {
        return Save(
            id = id,
            name = name,
            game = game,
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    constructor(save: Save) : this(
        id = save.id,
        name = save.name,
        game = save.game,
    )
}