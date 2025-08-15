/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 13:01
 */

package dev.bittim.encountr.core.data.defs.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DefinitionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(definitions: List<DefinitionEntity>)

    @Query("SELECT * FROM definition WHERE game = :game")
    suspend fun getDefinition(game: Int): DefinitionEntity?

    @Query("DELETE FROM definition")
    suspend fun deleteAll()
}