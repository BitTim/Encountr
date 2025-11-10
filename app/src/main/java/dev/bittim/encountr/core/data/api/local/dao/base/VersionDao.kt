/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.dao.base

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionStub
import dev.bittim.encountr.core.data.api.local.entity.reltaion.version.VersionFull
import kotlinx.coroutines.flow.Flow

@Dao
interface VersionDao {
    // region:      -- Create / Update

    @Upsert
    suspend fun upsertStub(versionStub: VersionStub)

    @Upsert
    suspend fun upsertStub(versionStubs: List<VersionStub>)

    @Upsert
    suspend fun upsertDetail(versionDetailEntity: VersionDetailEntity)

    @Upsert
    suspend fun upsertDetail(versionDetailEntities: List<VersionDetailEntity>)

    @Upsert
    suspend fun upsertLocalizedName(versionLocalizedNameEntity: VersionLocalizedNameEntity)

    @Upsert
    suspend fun upsertLocalizedName(versionLocalizedNameEntities: List<VersionLocalizedNameEntity>)

    // endregion:   -- Create / Update
    // region:      -- Read

    @Transaction
    @Query("SELECT * FROM version_stub WHERE id = :id")
    fun get(id: Int): Flow<VersionFull?>

    @Query("SELECT id FROM version_stub")
    fun getIds(): Flow<List<Int>>

    // endregion:   -- Read
    // region:      -- Delete

    @Query("DELETE FROM version_stub")
    suspend fun delete()

    // endregion:   -- Delete
}