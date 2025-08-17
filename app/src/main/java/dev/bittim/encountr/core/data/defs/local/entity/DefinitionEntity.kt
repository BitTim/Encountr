/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.08.25, 03:19
 */

package dev.bittim.encountr.core.data.defs.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.bittim.encountr.core.data.defs.local.converter.IntListConverter

@Entity(tableName = "definition")
@TypeConverters(IntListConverter::class)
data class DefinitionEntity(
    @PrimaryKey val id: Int = 0,
    val ignored: List<Int>,
)
