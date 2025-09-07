/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LinkedVersionGroupDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.09.25, 23:54
 */

package dev.bittim.encountr.core.data.defs.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.bittim.encountr.core.data.defs.local.entity.LinkedVersionGroupEntity

@Dao
interface LinkedVersionGroupDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(definitions: List<LinkedVersionGroupEntity>)

    @Query("SELECT * FROM linked_version_group WHERE parent = :parent")
    suspend fun getLinkedVersionGroup(parent: Int): LinkedVersionGroupEntity?

    @Query("DELETE FROM linked_version_group")
    suspend fun deleteAll()
}