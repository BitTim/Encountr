/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionGroupDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.11.25, 15:50
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

    @Transaction
    @Query(
        """
        SELECT version_stub.id FROM version_group_stub
        LEFT JOIN version_stub ON version_group_stub.id = version_stub.versionGroupId
        WHERE version_group_stub.id = :id AND version_stub.isIgnored IS FALSE
    """
    )
    fun getVersionIds(id: Int): Flow<List<Int>>

    // endregion:   -- Read
    // region:      -- Delete

    @Query("DELETE FROM version_group_stub")
    suspend fun delete()

    // endregion:   -- Delete
}