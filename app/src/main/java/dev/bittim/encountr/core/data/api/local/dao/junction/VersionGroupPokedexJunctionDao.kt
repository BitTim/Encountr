/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionGroupPokedexJunctionDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 02:31
 */

package dev.bittim.encountr.core.data.api.local.dao.junction

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.bittim.encountr.core.data.api.local.entity.junction.VersionGroupPokedexJunction
import kotlinx.coroutines.flow.Flow

@Dao
interface VersionGroupPokedexJunctionDao {
    // region:      -- Create / Update

    @Upsert
    suspend fun upsert(versionGroupPokedexJunction: VersionGroupPokedexJunction)

    @Upsert
    suspend fun upsert(versionGroupPokedexJunctions: List<VersionGroupPokedexJunction>)

    // endregion:   -- Create / Update
    // region:      -- Read

    @Query("SELECT versionGroupId FROM version_group_pokedex_junction WHERE pokedexId = :pokedexId")
    fun getVersionGroupIdsByPokedex(pokedexId: Int): Flow<List<Int>>

    @Query("SELECT pokedexId FROM version_group_pokedex_junction WHERE versionGroupId = :versionGroupId")
    fun getPokedexIdsByVersionGroup(versionGroupId: Int): Flow<List<Int>>

    // endregion:   -- Read
    // region:      -- Delete

    @Query("DELETE FROM version_group_pokedex_junction")
    suspend fun delete()

    // endregion:   -- Delete
}