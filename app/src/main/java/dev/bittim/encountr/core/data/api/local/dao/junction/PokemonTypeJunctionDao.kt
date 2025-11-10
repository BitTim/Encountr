/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonTypeJunctionDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.dao.junction

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.bittim.encountr.core.data.api.local.entity.junction.PokemonTypeJunction
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonTypeJunctionDao {
    // region:      -- Create / Update

    @Upsert
    suspend fun upsert(pokemonTypeJunction: PokemonTypeJunction)

    @Upsert
    suspend fun upsert(pokemonTypeJunction: List<PokemonTypeJunction>)

    // endregion:   -- Create / Update
    // region:      -- Read

    @Query("SELECT * FROM pokemon_type_junction WHERE pokemonId = :pokemonId")
    fun getByPokemonOverview(pokemonId: Int): Flow<List<PokemonTypeJunction>>

    @Query("SELECT * FROM pokemon_type_junction WHERE typeId = :typeId")
    fun getByType(typeId: Int): Flow<List<PokemonTypeJunction>>

    // endregion:   -- Read
    // region:      -- Delete

    @Query("DELETE FROM pokemon_type_junction")
    suspend fun delete()

    // endregion:   -- Delete
}