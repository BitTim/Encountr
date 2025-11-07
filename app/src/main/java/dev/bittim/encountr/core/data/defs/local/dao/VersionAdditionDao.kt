/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionAdditionDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.defs.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.bittim.encountr.core.data.defs.local.entity.VersionAdditionEntity

@Dao
interface VersionAdditionDao {
    @Upsert
    suspend fun upsert(iconDefinitionEntities: List<VersionAdditionEntity>)

    @Query("SELECT * FROM version_addition WHERE versionId = :versionId LIMIT 1")
    suspend fun get(versionId: Int): VersionAdditionEntity?

    @Query("DELETE FROM version_addition")
    suspend fun delete()
}