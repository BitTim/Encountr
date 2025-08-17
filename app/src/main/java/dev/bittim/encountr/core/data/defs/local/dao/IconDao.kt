/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       IconDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.08.25, 03:10
 */

package dev.bittim.encountr.core.data.defs.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.bittim.encountr.core.data.defs.local.entity.IconDefinitionEntity

@Dao
interface IconDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(definitions: List<IconDefinitionEntity>)

    @Query("SELECT * FROM icons WHERE game = :game")
    suspend fun getDefinition(game: Int): IconDefinitionEntity?

    @Query("DELETE FROM icons")
    suspend fun deleteAll()
}