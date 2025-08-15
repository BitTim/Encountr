/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SaveDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 14:21
 */

package dev.bittim.encountr.core.data.user.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.bittim.encountr.core.data.user.local.entity.SaveEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SaveDao {
    // region:      -- Create

    @Insert
    suspend fun insert(save: SaveEntity)

    @Insert
    suspend fun insert(saves: List<SaveEntity>)

    // endregion:   -- Create
    // region:      -- Read

    @Query("SELECT * FROM saves WHERE id = :id")
    fun get(id: String): Flow<SaveEntity?>

    @Query("SELECT * FROM saves")
    fun getAll(): Flow<List<SaveEntity>>

    // endregion:   -- Read
    // region:      -- Update

    @Query("UPDATE saves SET name = :name, game = :game WHERE id = :id")
    suspend fun update(id: String, name: String, game: Int)

    @Query("UPDATE saves SET name = :name WHERE id = :id")
    suspend fun updateName(id: String, name: String)

    @Query("UPDATE saves SET game = :game WHERE id = :id")
    suspend fun updateGame(id: String, game: Int)

    // endregion:   -- Update1
    // region:      -- Delete

    @Query("DELETE FROM saves WHERE id = :id")
    suspend fun delete(id: String)

    @Query("DELETE FROM saves")
    suspend fun deleteAll()

    // endregion:   -- Delete
}