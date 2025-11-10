/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TypeDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.dao.base

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeSpriteEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeStub
import dev.bittim.encountr.core.data.api.local.entity.reltaion.type.TypeFull
import kotlinx.coroutines.flow.Flow

@Dao
interface TypeDao {
    // region:      -- Create / Update

    @Upsert
    suspend fun upsertStub(typeStub: TypeStub)

    @Upsert
    suspend fun upsertStub(typeStubs: List<TypeStub>)

    @Upsert
    suspend fun upsertDetail(typeDetailEntity: TypeDetailEntity)

    @Upsert
    suspend fun upsertDetail(typeDetailEntities: List<TypeDetailEntity>)

    @Upsert
    suspend fun upsertLocalizedName(typeLocalizedNameEntity: TypeLocalizedNameEntity)

    @Upsert
    suspend fun upsertLocalizedName(typeLocalizedNameEntities: List<TypeLocalizedNameEntity>)

    @Upsert
    suspend fun upsertSprite(typeSpriteEntity: TypeSpriteEntity)

    @Upsert
    suspend fun upsertSprite(typeSpriteEntities: List<TypeSpriteEntity>)

    // endregion:   -- Create / Update
    // region:      -- Read

    @Transaction
    @Query("SELECT * FROM type_stub WHERE id = :id")
    fun get(id: Int): Flow<TypeFull?>

    @Query("SELECT id FROM type_stub")
    fun getIds(): Flow<List<Int>>

    // endregion:   -- Read
    // region:      -- Delete

    @Query("DELETE FROM type_stub")
    suspend fun delete()

    // endregion:   -- Delete
}