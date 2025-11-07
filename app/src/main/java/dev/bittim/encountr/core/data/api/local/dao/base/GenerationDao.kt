/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GenerationDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.dao.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationEntity
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.reltaion.generation.GenerationFull
import kotlinx.coroutines.flow.Flow

@Dao
interface GenerationDao {
    // region:      -- Create / Update

    @Insert(onConflict = IGNORE)
    suspend fun insert(generationEntity: GenerationEntity)

    @Transaction
    @Upsert
    suspend fun upsert(
        generationEntity: GenerationEntity,
        generationLocalizedNameEntities: List<GenerationLocalizedNameEntity>,
    )

    @Transaction
    @Upsert
    suspend fun upsert(
        generationEntities: List<GenerationEntity>,
        generationLocalizedNameEntities: List<GenerationLocalizedNameEntity>,
    )

    // endregion:   -- Create / Update
    // region:      -- Read

    @Transaction
    @Query(
        """
        SELECT generation.*, version_group.id FROM generation
        LEFT JOIN version_group ON version_group.generationId = generation.id
        WHERE generation.id = :id
        """
    )
    fun get(id: Int): Flow<GenerationFull?>

    @Transaction
    @Query(
        """
        SELECT generation.*, version_group.id FROM generation
        LEFT JOIN version_group ON version_group.generationId = generation.id
        """
    )
    fun get(): Flow<List<GenerationFull>>

    // endregion:   -- Read
    // region:      -- Delete

    @Query("DELETE FROM generation")
    suspend fun delete()

    // endregion:   -- Delete
}