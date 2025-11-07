/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.defs.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "definition")
data class DefinitionEntity(
    @PrimaryKey val id: Int = 0,
    val imageUrl: String?,
    val ignoredVersionIds: List<Int>,
)
