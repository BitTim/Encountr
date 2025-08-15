/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TeamDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   14.08.25, 22:24
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
    @Insert
    suspend fun insert(team: TeamEntity)

    @Insert
    suspend fun insert(teams: List<TeamEntity>)

    @OptIn(ExperimentalUuidApi::class)
    @Transaction
    @Query("SELECT * FROM teams WHERE id = :id")
    fun get(id: Uuid): Flow<TeamWithPokemon?>

    @OptIn(ExperimentalUuidApi::class)
    @Transaction
    @Query("SELECT * FROM teams WHERE saveId = :saveId")
    fun getAll(saveId: Uuid): Flow<List<TeamWithPokemon>>

    @OptIn(ExperimentalUuidApi::class)
    @Query("DELETE FROM teams WHERE id = :id AND saveId = :saveId")
    suspend fun delete(id: Uuid, saveId: Uuid)

    @OptIn(ExperimentalUuidApi::class)
    @Query("DELETE FROM teams WHERE saveId = :saveId")
    suspend fun deleteAll(saveId: Uuid)
}