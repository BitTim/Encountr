/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LanguageDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.dao.base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Upsert
import dev.bittim.encountr.core.data.api.local.entity.base.language.LanguageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LanguageDao {
    // region:      -- Create / Update

    @Insert(onConflict = IGNORE)
    suspend fun insert(languageEntity: LanguageEntity)

    @Upsert
    suspend fun upsert(languageEntity: LanguageEntity)

    @Upsert
    suspend fun upsert(languageEntities: List<LanguageEntity>)

    // endregion:   -- Create / Update
    // region:      -- Read

    @Query("SELECT * FROM language WHERE id = :id AND localizedName NOT NULL")
    fun get(id: Int): Flow<LanguageEntity?>

    @Query("SELECT * FROM language WHERE localizedName NOT NULL")
    fun get(): Flow<List<LanguageEntity>>

    // endregion:   -- Region
    // region:      -- Delete

    @Query("DELETE FROM language")
    suspend fun delete()

    // endregion:   -- Delete
}