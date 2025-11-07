/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TypeDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.dao.base

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeSpriteEntity
import dev.bittim.encountr.core.data.api.local.entity.reltaion.type.TypeFull
import kotlinx.coroutines.flow.Flow

@Dao
interface TypeDao {
    // region:      -- Create / Update

    @Transaction
    @Upsert
    suspend fun upsert(
        typeEntity: TypeEntity,
        typeLocalizedNameEntities: List<TypeLocalizedNameEntity>,
        typeSpritesEntities: List<TypeSpriteEntity>
    )

    @Transaction
    @Upsert
    suspend fun upsert(
        typeEntities: List<TypeEntity>,
        typeLocalizedNameEntities: List<TypeLocalizedNameEntity>,
        typeSpritesEntities: List<TypeSpriteEntity>
    )

    // endregion:   -- Create / Update
    // region:      -- Read

    @Transaction
    @Query("SELECT * FROM type WHERE id = :id")
    fun get(id: Int): Flow<TypeFull?>

    @Transaction
    @Query("SELECT * FROM type")
    fun get(): Flow<List<TypeFull>>

    // endregion:   -- Read
    // region:      -- Delete

    @Query("DELETE FROM type")
    suspend fun delete()

    // endregion:   -- Delete
}