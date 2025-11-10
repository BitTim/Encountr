/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GenerationDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.dao.base

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationStub
import dev.bittim.encountr.core.data.api.local.entity.reltaion.generation.GenerationFull
import kotlinx.coroutines.flow.Flow

@Dao
interface GenerationDao {
    // region:      -- Create / Update

    @Upsert
    suspend fun upsertStub(generationStub: GenerationStub)

    @Upsert
    suspend fun upsertStub(generationStubs: List<GenerationStub>)

    @Upsert
    suspend fun upsertDetail(generationDetailEntity: GenerationDetailEntity)

    @Upsert
    suspend fun upsertDetail(generationDetailEntities: List<GenerationDetailEntity>)

    @Upsert
    suspend fun upsertLocalizedName(generationLocalizedNameEntity: GenerationLocalizedNameEntity)

    @Upsert
    suspend fun upsertLocalizedName(generationLocalizedNameEntities: List<GenerationLocalizedNameEntity>)

    // endregion:   -- Create / Update
    // region:      -- Read

    @Transaction
    @Query("SELECT * FROM generation_stub WHERE id = :id")
    fun get(id: Int): Flow<GenerationFull?>

    @Query("SELECT id FROM generation_stub")
    fun getIds(): Flow<List<Int>>

    // endregion:   -- Read
    // region:      -- Delete

    @Query("DELETE FROM generation_stub")
    suspend fun delete()

    // endregion:   -- Delete
}