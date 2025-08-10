/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionsDatabase.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.08.25, 02:01
 */

package dev.bittim.encountr.core.data.defs

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.bittim.encountr.core.data.defs.dao.DefinitionDao
import dev.bittim.encountr.core.data.defs.dao.VersionDao
import dev.bittim.encountr.core.data.defs.entity.Definition
import dev.bittim.encountr.core.data.defs.entity.DefinitionsVersion

@Database(
    entities = [DefinitionsVersion::class, Definition::class],
    version = 1
)
abstract class DefinitionsDatabase : RoomDatabase() {
    abstract fun versionDao(): VersionDao
    abstract fun definitionDao(): DefinitionDao
}