/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonTeamRefDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 11:02
 */

package dev.bittim.encountr.core.data.user.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.bittim.encountr.core.data.user.local.entity.PokemonTeamCrossRef
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Dao
interface PokemonTeamRefDao {
    // region:      -- Create

    @Insert
    suspend fun insert(pokemonTeamCrossRef: PokemonTeamCrossRef)

    @Insert
    suspend fun insert(pokemonTeamCrossRefs: List<PokemonTeamCrossRef>)

    // endregion:   -- Create
    // region:      -- Read

    @OptIn(ExperimentalUuidApi::class)
    @Query("SELECT * FROM pokemon_team_ref WHERE team = :team")
    suspend fun getByTeam(team: Uuid): Flow<List<PokemonTeamCrossRef>>

    // endregion:   -- Read
    // region:      -- Delete

    @OptIn(ExperimentalUuidApi::class)
    @Query("DELETE FROM pokemon_team_ref WHERE save = :save AND team = :team AND pokemon = :pokemon")
    suspend fun delete(save: Uuid, team: Uuid, pokemon: Int)

    @OptIn(ExperimentalUuidApi::class)
    @Query("DELETE FROM pokemon_team_ref WHERE team = :team")
    suspend fun deleteByTeam(team: Uuid)

    // endregion:   -- Delete
}