/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonStateDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.user.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.bittim.encountr.core.data.user.local.entity.PokemonStateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonStateDao {
    // region:      -- Create

    @Insert
    suspend fun insert(pokemon: PokemonStateEntity)

    @Insert
    suspend fun insert(pokemon: List<PokemonStateEntity>)

    // endregion:   -- Create
    // region:      -- Read

    @Query("SELECT * FROM pokemon_state WHERE save = :save AND id = :id")
    fun get(save: String, id: Int): Flow<PokemonStateEntity?>

    @Query("SELECT * FROM pokemon_state WHERE save = :save")
    fun getAll(save: String): Flow<List<PokemonStateEntity>>

    // endregion:   -- Read
    // region:      -- Update

    @Query("UPDATE pokemon_state SET caught = :caught WHERE id = :id AND save = :save")
    suspend fun update(save: String, id: Int, caught: Boolean)

    // endregion:   -- Update
    // region:      -- Delete

    @Query("DELETE FROM pokemon_state WHERE id = :id AND save = :save")
    suspend fun delete(save: String, id: Int)

    @Query("DELETE FROM pokemon_state WHERE save = :save")
    suspend fun deleteAll(save: String)

    // endregion:   -- Delete
}