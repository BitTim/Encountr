/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TeamDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 09:25
 */

package dev.bittim.encountr.core.data.user.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import dev.bittim.encountr.core.data.user.local.entity.TeamEntity
import dev.bittim.encountr.core.data.user.local.relation.TeamWithPokemon
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Dao
interface TeamDao {
    // region:      -- Create

    @Insert
    suspend fun insert(team: TeamEntity)

    @Insert
    suspend fun insert(teams: List<TeamEntity>)

    // endregion:   -- Create
    // region:      -- Read

    @OptIn(ExperimentalUuidApi::class)
    @Transaction
    @Query("SELECT * FROM teams WHERE save = :save AND id = :id")
    fun get(save: Uuid, id: Uuid): Flow<TeamWithPokemon?>

    @OptIn(ExperimentalUuidApi::class)
    @Transaction
    @Query("SELECT * FROM teams WHERE save = :save")
    fun getAll(save: Uuid): Flow<List<TeamWithPokemon>>

    // endregion:   -- Read
    // region:      -- Update

    @OptIn(ExperimentalUuidApi::class)
    @Query("UPDATE teams SET name = :name WHERE save = :save AND id = :id")
    suspend fun update(save: Uuid, id: Uuid, name: String)

    // endregion:   -- Update
    // region:      -- Delete

    @OptIn(ExperimentalUuidApi::class)
    @Query("DELETE FROM teams WHERE save = :save AND id = :id")
    suspend fun delete(save: Uuid, id: Uuid)

    @OptIn(ExperimentalUuidApi::class)
    @Query("DELETE FROM teams WHERE save = :save")
    suspend fun deleteAll(save: Uuid)

    // endregion:   -- Delete
}