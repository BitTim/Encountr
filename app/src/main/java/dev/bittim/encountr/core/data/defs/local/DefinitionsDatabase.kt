/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionsDatabase.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.08.25, 17:37
 */

package dev.bittim.encountr.core.data.defs.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DefinitionEntity::class],
    version = 1
)
abstract class DefinitionsDatabase : RoomDatabase() {
    abstract fun definitionDao(): DefinitionDao
}