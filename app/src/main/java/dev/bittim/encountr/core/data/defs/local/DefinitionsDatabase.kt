/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionsDatabase.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.defs.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.bittim.encountr.core.data.common.converter.IntListConverter
import dev.bittim.encountr.core.data.defs.local.dao.DefinitionDao
import dev.bittim.encountr.core.data.defs.local.dao.PokedexAdditionDao
import dev.bittim.encountr.core.data.defs.local.dao.VersionAdditionDao
import dev.bittim.encountr.core.data.defs.local.entity.DefinitionEntity
import dev.bittim.encountr.core.data.defs.local.entity.PokedexAdditionEntity
import dev.bittim.encountr.core.data.defs.local.entity.VersionAdditionEntity

@TypeConverters(IntListConverter::class)
@Database(
    version = 1,
    entities = [
        DefinitionEntity::class, VersionAdditionEntity::class, PokedexAdditionEntity::class
    ],
)
abstract class DefinitionsDatabase : RoomDatabase() {
    abstract fun definitionDao(): DefinitionDao
    abstract fun versionAdditionDao(): VersionAdditionDao
    abstract fun pokedexAdditionDao(): PokedexAdditionDao
}