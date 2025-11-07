/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonTeamRefDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.user.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.bittim.encountr.core.data.user.local.entity.PokemonTeamJunction
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonTeamRefDao {
    // region:      -- Create

    @Upsert
    suspend fun insert(pokemonTeamJunction: PokemonTeamJunction)

    @Upsert
    suspend fun insert(pokemonTeamJunctions: List<PokemonTeamJunction>)

    // endregion:   -- Create
    // region:      -- Read

    @Query("SELECT * FROM pokemon_team_junction WHERE team = :team")
    fun getByTeam(team: String): Flow<List<PokemonTeamJunction>>

    // endregion:   -- Read
    // region:      -- Delete

    @Query("DELETE FROM pokemon_team_junction WHERE team = :team AND pokemonId = :pokemonId")
    suspend fun delete(team: String, pokemonId: Int)

    @Query("DELETE FROM pokemon_team_junction WHERE team = :team")
    suspend fun deleteByTeam(team: String)

    // endregion:   -- Delete
}