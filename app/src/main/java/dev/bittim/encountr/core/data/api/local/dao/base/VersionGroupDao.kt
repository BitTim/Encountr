/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionGroupDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.dao.base

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import dev.bittim.encountr.core.data.api.local.entity.base.versionGroup.VersionGroupDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.versionGroup.VersionGroupStub
import dev.bittim.encountr.core.data.api.local.entity.reltaion.versionGroup.VersionGroupFull
import kotlinx.coroutines.flow.Flow

@Dao
interface VersionGroupDao {
    // region:      -- Create / Update

    @Upsert
    suspend fun upsertStub(versionGroupStub: VersionGroupStub)

    @Upsert
    suspend fun upsertStub(versionGroupStubs: List<VersionGroupStub>)

    @Upsert
    suspend fun upsertDetail(versionGroupDetailEntity: VersionGroupDetailEntity)

    @Upsert
    suspend fun upsertDetail(versionGroupEntities: List<VersionGroupDetailEntity>)

    // endregion:   -- Create / Update
    // region:      -- Read

    @Transaction
    @Query("SELECT * FROM version_group_stub WHERE id = :id")
    fun get(id: Int): Flow<VersionGroupFull?>

    @Query("SELECT id FROM version_group_stub")
    fun getIds(): Flow<List<Int>>

    // endregion:   -- Read
    // region:      -- Delete

    @Query("DELETE FROM version_group_stub")
    suspend fun delete()

    // endregion:   -- Delete
}