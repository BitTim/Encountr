/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LanguageDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.dao.base

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import dev.bittim.encountr.core.data.api.local.entity.base.language.LanguageDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.language.LanguageStub
import dev.bittim.encountr.core.data.api.local.entity.reltaion.language.LanguageFull
import kotlinx.coroutines.flow.Flow

@Dao
interface LanguageDao {
    // region:      -- Create / Update

    @Upsert
    suspend fun upsertStub(languageStub: LanguageStub)

    @Upsert
    suspend fun upsertStub(languageStubs: List<LanguageStub>)

    @Upsert
    suspend fun upsertDetail(languageDetailEntity: LanguageDetailEntity)

    @Upsert
    suspend fun upsertDetail(languageDetailEntities: List<LanguageDetailEntity>)

    // endregion:   -- Create / Update
    // region:      -- Read

    @Transaction
    @Query("SELECT * FROM language_stub WHERE id = :id")
    fun get(id: Int): Flow<LanguageFull?>

    @Query("SELECT id FROM language_stub WHERE isLocalized IS TRUE")
    fun getIds(): Flow<List<Int>>

    // endregion:   -- Region
    // region:      -- Delete

    @Query("DELETE FROM language_stub")
    suspend fun delete()

    // endregion:   -- Delete
}