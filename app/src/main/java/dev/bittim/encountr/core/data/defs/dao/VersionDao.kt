/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.08.25, 02:01
 */

package dev.bittim.encountr.core.data.defs.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.bittim.encountr.core.data.defs.entity.DefinitionsVersion

@Dao
interface VersionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(definitionsVersion: DefinitionsVersion)

    @Query("SELECT * FROM version WHERE id = 0 LIMIT 1")
    suspend fun getVersion(): DefinitionsVersion?

    @Query("DELETE FROM version")
    suspend fun deleteAll()
}