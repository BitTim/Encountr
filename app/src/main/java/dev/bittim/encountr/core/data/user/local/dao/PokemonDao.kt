/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   14.08.25, 22:24
 */

package dev.bittim.encountr.core.data.user.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.bittim.encountr.core.data.user.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Dao
interface PokemonDao {
    @Insert
    suspend fun insert(pokemon: PokemonEntity)

    @Insert
    suspend fun insert(pokemon: List<PokemonEntity>)

    @OptIn(ExperimentalUuidApi::class)
    @Query("SELECT * FROM pokemon WHERE id = :id")
    fun get(id: Uuid): Flow<PokemonEntity?>

    @OptIn(ExperimentalUuidApi::class)
    @Query("SELECT * FROM pokemon WHERE saveId = :saveId")
    fun getAll(saveId: Uuid): Flow<List<PokemonEntity>>

    @OptIn(ExperimentalUuidApi::class)
    @Query("DELETE FROM pokemon WHERE id = :id AND saveId = :saveId")
    suspend fun delete(id: Uuid, saveId: Uuid)

    @OptIn(ExperimentalUuidApi::class)
    @Query("DELETE FROM pokemon WHERE saveId = :saveId")
    suspend fun deleteAll(saveId: Uuid)
}