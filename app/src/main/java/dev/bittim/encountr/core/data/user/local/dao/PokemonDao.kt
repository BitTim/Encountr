/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 14:20
 */

package dev.bittim.encountr.core.data.user.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.bittim.encountr.core.data.user.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    // region:      -- Create

    @Insert
    suspend fun insert(pokemon: PokemonEntity)

    @Insert
    suspend fun insert(pokemon: List<PokemonEntity>)

    // endregion:   -- Create
    // region:      -- Read

    @Query("SELECT * FROM pokemon WHERE save = :save AND id = :id")
    fun get(save: String, id: Int): Flow<PokemonEntity?>

    @Query("SELECT * FROM pokemon WHERE save = :save")
    fun getAll(save: String): Flow<List<PokemonEntity>>

    // endregion:   -- Read
    // region:      -- Update

    @Query("UPDATE pokemon SET caught = :caught WHERE id = :id AND save = :save")
    suspend fun update(save: String, id: Int, caught: Boolean)

    // endregion:   -- Update
    // region:      -- Delete

    @Query("DELETE FROM pokemon WHERE id = :id AND save = :save")
    suspend fun delete(save: String, id: Int)

    @Query("DELETE FROM pokemon WHERE save = :save")
    suspend fun deleteAll(save: String)

    // endregion:   -- Delete
}