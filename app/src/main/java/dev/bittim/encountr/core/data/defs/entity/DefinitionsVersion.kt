/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionsVersion.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.08.25, 02:01
 */

package dev.bittim.encountr.core.data.defs.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "version")
data class DefinitionsVersion(
    @PrimaryKey val id: Int = 0,
    val version: Int,
)
