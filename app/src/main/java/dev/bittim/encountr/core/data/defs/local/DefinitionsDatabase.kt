/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionsDatabase.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.09.25, 23:54
 */

package dev.bittim.encountr.core.data.defs.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.bittim.encountr.core.data.defs.local.converter.IntListConverter
import dev.bittim.encountr.core.data.defs.local.dao.DefinitionDao
import dev.bittim.encountr.core.data.defs.local.dao.IconDao
import dev.bittim.encountr.core.data.defs.local.dao.LinkedVersionGroupDao
import dev.bittim.encountr.core.data.defs.local.entity.DefinitionEntity
import dev.bittim.encountr.core.data.defs.local.entity.IconDefinitionEntity
import dev.bittim.encountr.core.data.defs.local.entity.LinkedVersionGroupEntity

@Database(
    entities = [DefinitionEntity::class, IconDefinitionEntity::class, LinkedVersionGroupEntity::class],
    version = 1
)
@TypeConverters(IntListConverter::class)
abstract class DefinitionsDatabase : RoomDatabase() {
    abstract fun definitionDao(): DefinitionDao
    abstract fun iconDao(): IconDao
    abstract fun linkedVersionGroupDao(): LinkedVersionGroupDao
}