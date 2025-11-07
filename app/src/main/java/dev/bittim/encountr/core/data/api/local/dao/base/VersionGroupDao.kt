/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionGroupDao.kt
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
import dev.bittim.encountr.core.data.api.local.entity.base.versionGroup.VersionGroupEntity
import dev.bittim.encountr.core.data.api.local.entity.junction.VersionGroupPokedexJunction
import dev.bittim.encountr.core.data.api.local.entity.reltaion.versionGroup.VersionGroupFull
import kotlinx.coroutines.flow.Flow

@Dao
interface VersionGroupDao {
    // region:      -- Create / Update

    @Insert(onConflict = IGNORE)
    suspend fun insert(versionGroupEntity: VersionGroupEntity)

    @Transaction
    @Upsert
    suspend fun upsert(
        versionGroupEntity: VersionGroupEntity,
        versionGroupPokedexJunctions: List<VersionGroupPokedexJunction>
    )

    @Transaction
    @Upsert
    suspend fun upsert(
        versionGroupEntities: List<VersionGroupEntity>,
        versionGroupPokedexJunctions: List<VersionGroupPokedexJunction>
    )

    // endregion:   -- Create / Update
    // region:      -- Read

    @Transaction
    @Query(
        """
        SELECT version_group.*, version.id FROM version_group
        LEFT JOIN version ON version.versionGroupId = version_group.id
        WHERE version_group.id = :id
        """
    )
    fun get(id: Int): Flow<VersionGroupFull?>

    @Transaction
    @Query(
        """
        SELECT version_group.*, version.id FROM version_group
        LEFT JOIN version ON version.versionGroupId = version_group.id
        """
    )
    fun get(): Flow<List<VersionGroupFull>>

    // endregion:   -- Read
    // region:      -- Delete

    @Query("DELETE FROM version")
    suspend fun delete()

    // endregion:   -- Delete
}