/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionDao.kt
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
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionEntity
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.reltaion.version.VersionFull
import kotlinx.coroutines.flow.Flow

@Dao
interface VersionDao {
    // region:      -- Create / Update

    @Insert(onConflict = IGNORE)
    suspend fun insert(versionEntity: VersionEntity)

    @Transaction
    @Upsert
    suspend fun upsert(
        versionEntity: VersionEntity,
        versionLocalizedNameEntities: List<VersionLocalizedNameEntity>,
    )

    @Transaction
    @Upsert
    suspend fun upsert(
        versionEntities: List<VersionEntity>,
        versionLocalizedNameEntities: List<VersionLocalizedNameEntity>,
    )

    // endregion:   -- Create / Update
    // region:      -- Read

    @Transaction
    @Query("SELECT * FROM version WHERE id = :id")
    fun get(id: Int): Flow<VersionFull?>

    @Transaction
    @Query("SELECT * FROM version")
    fun get(): Flow<List<VersionFull>>

    // endregion:   -- Read
    // region:      -- Delete

    @Query("DELETE FROM version")
    suspend fun delete()

    // endregion:   -- Delete
}