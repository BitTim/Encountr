/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Definition.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.08.25, 02:01
 */

package dev.bittim.encountr.core.data.defs.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "definition")
data class Definition(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val game: String,
    val pokemon: String,
    val from: String?
)
