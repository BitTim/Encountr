/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.08.25, 03:13
 */

package dev.bittim.encountr.core.data.defs.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.bittim.encountr.core.data.defs.local.entity.DefinitionEntity

@Dao
interface DefinitionDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(definitions: List<DefinitionEntity>)

    @Query("SELECT * FROM definition WHERE id = 0")
    suspend fun getDefinition(): DefinitionEntity?

    @Query("DELETE FROM definition")
    suspend fun deleteAll()
}