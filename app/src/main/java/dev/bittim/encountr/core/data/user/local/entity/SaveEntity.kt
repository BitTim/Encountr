/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SaveEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   14.08.25, 22:28
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
    val game: String,
) {
    @OptIn(ExperimentalUuidApi::class)
    fun toModel(): Save {
        return Save(
            id = id,
            name = name,
            game = game,
        )
    }
}