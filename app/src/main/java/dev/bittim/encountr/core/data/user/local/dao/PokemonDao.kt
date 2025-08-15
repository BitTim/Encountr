/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 10:52
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
    // region:      -- Create

    @Insert
    suspend fun insert(pokemon: PokemonEntity)

    @Insert
    suspend fun insert(pokemon: List<PokemonEntity>)

    // endregion:   -- Create
    // region:      -- Read

    @OptIn(ExperimentalUuidApi::class)
    @Query("SELECT * FROM pokemon WHERE save = :save AND id = :id")
    fun get(save: Uuid, id: Int): Flow<PokemonEntity?>

    @OptIn(ExperimentalUuidApi::class)
    @Query("SELECT * FROM pokemon WHERE save = :save")
    fun getAll(save: Uuid): Flow<List<PokemonEntity>>

    // endregion:   -- Read
    // region:      -- Update

    @OptIn(ExperimentalUuidApi::class)
    @Query("UPDATE pokemon SET caught = :caught WHERE id = :id AND save = :save")
    suspend fun update(save: Uuid, id: Int, caught: Boolean)

    // endregion:   -- Update
    // region:      -- Delete

    @OptIn(ExperimentalUuidApi::class)
    @Query("DELETE FROM pokemon WHERE id = :id AND save = :save")
    suspend fun delete(save: Uuid, id: Int)

    @OptIn(ExperimentalUuidApi::class)
    @Query("DELETE FROM pokemon WHERE save = :save")
    suspend fun deleteAll(save: Uuid)

    // endregion:   -- Delete
}