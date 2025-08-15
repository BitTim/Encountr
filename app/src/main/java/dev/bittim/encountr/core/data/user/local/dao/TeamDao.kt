/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TeamDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 14:21
 */

package dev.bittim.encountr.core.data.user.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import dev.bittim.encountr.core.data.user.local.entity.TeamEntity
import dev.bittim.encountr.core.data.user.local.relation.TeamWithPokemon
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    // region:      -- Create

    @Insert
    suspend fun insert(team: TeamEntity)

    @Insert
    suspend fun insert(teams: List<TeamEntity>)

    // endregion:   -- Create
    // region:      -- Read

    @Transaction
    @Query("SELECT * FROM teams WHERE save = :save AND id = :id")
    fun get(save: String, id: String): Flow<TeamWithPokemon?>

    @Transaction
    @Query("SELECT * FROM teams WHERE save = :save")
    fun getAll(save: String): Flow<List<TeamWithPokemon>>

    // endregion:   -- Read
    // region:      -- Update

    @Query("UPDATE teams SET name = :name WHERE save = :save AND id = :id")
    suspend fun update(save: String, id: String, name: String)

    // endregion:   -- Update
    // region:      -- Delete

    @Query("DELETE FROM teams WHERE save = :save AND id = :id")
    suspend fun delete(save: String, id: String)

    @Query("DELETE FROM teams WHERE save = :save")
    suspend fun deleteAll(save: String)

    // endregion:   -- Delete
}