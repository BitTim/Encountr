/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SaveDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 02:35
 */

package dev.bittim.encountr.core.data.user.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.bittim.encountr.core.data.user.local.entity.SaveEntity
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Dao
interface SaveDao {
    // region:      -- Create

    @Insert
    suspend fun insert(save: SaveEntity)

    @Insert
    suspend fun insert(saves: List<SaveEntity>)

    // endregion:   -- Create
    // region:      -- Read

    @OptIn(ExperimentalUuidApi::class)
    @Query("SELECT * FROM saves WHERE id = :id")
    fun get(id: Uuid): Flow<SaveEntity?>

    @Query("SELECT * FROM saves")
    fun getAll(): Flow<List<SaveEntity>>

    // endregion:   -- Read
    // region:      -- Update

    @OptIn(ExperimentalUuidApi::class)
    @Query("UPDATE saves SET name = :name, game = :game WHERE id = :id")
    suspend fun update(id: Uuid, name: String, game: String)

    @OptIn(ExperimentalUuidApi::class)
    @Query("UPDATE saves SET name = :name WHERE id = :id")
    suspend fun updateName(id: Uuid, name: String)

    @OptIn(ExperimentalUuidApi::class)
    @Query("UPDATE saves SET game = :game WHERE id = :id")
    suspend fun updateGame(id: Uuid, game: String)

    // endregion:   -- Update1
    // region:      -- Delete

    @OptIn(ExperimentalUuidApi::class)
    @Query("DELETE FROM saves WHERE id = :id")
    suspend fun delete(id: Uuid)

    @Query("DELETE FROM saves")
    suspend fun deleteAll()

    // endregion:   -- Delete
}