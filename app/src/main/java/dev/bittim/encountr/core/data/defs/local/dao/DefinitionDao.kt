/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.defs.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.bittim.encountr.core.data.defs.local.entity.DefinitionEntity

@Dao
interface DefinitionDao {
    @Upsert
    suspend fun upsert(definitions: List<DefinitionEntity>)

    @Query("SELECT * FROM definition WHERE id = 0 LIMIT 1")
    suspend fun get(): DefinitionEntity?

    @Query("DELETE FROM definition")
    suspend fun delete()
}