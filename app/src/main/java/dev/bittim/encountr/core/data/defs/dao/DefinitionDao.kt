/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.08.25, 02:01
 */

package dev.bittim.encountr.core.data.defs.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.bittim.encountr.core.data.defs.entity.Definition

@Dao
interface DefinitionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(definition: Definition)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(definitions: List<Definition>)

    @Query("SELECT * FROM definition WHERE id = :id LIMIT 1")
    suspend fun getDefinitionById(id: Int): Definition?

    @Query("SELECT * FROM definition WHERE game = :game")
    suspend fun getDefinitionByGame(game: String): Definition?

    @Query("DELETE FROM definition")
    suspend fun deleteAll()
}