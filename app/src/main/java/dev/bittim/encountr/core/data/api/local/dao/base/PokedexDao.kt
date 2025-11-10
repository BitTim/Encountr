/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.dao.base

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexStub
import dev.bittim.encountr.core.data.api.local.entity.reltaion.pokedex.PokedexFull
import kotlinx.coroutines.flow.Flow

@Dao
interface PokedexDao {
    // region:      -- Create

    @Upsert
    suspend fun upsertStub(pokedexStub: PokedexStub)

    @Upsert
    suspend fun upsertStub(pokedexStubs: List<PokedexStub>)

    @Upsert
    suspend fun upsertDetail(pokedexDetailEntity: PokedexDetailEntity)

    @Upsert
    suspend fun upsertDetail(pokedexDetailEntities: List<PokedexDetailEntity>)

    @Upsert
    suspend fun upsertLocalizedName(pokedexLocalizedNameEntity: PokedexLocalizedNameEntity)

    @Upsert
    suspend fun upsertLocalizedName(pokedexLocalizedNameEntities: List<PokedexLocalizedNameEntity>)

    // endregion:   -- Create
    // region:      -- Read

    @Transaction
    @Query("SELECT * FROM pokedex_stub WHERE id = :id")
    fun get(id: Int): Flow<PokedexFull?>

    @Query("SELECT id FROM pokedex_stub")
    fun getIds(): Flow<List<Int>>

    // endregion:   -- Read
    // region:      -- Delete

    @Transaction
    @Query("DELETE FROM pokedex_stub")
    suspend fun delete()

    // endregion:   -- Delete
}