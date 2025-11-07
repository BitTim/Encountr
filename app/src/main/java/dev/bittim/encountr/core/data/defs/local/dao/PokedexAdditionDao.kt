/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexAdditionDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.defs.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.bittim.encountr.core.data.defs.local.entity.PokedexAdditionEntity

@Dao
interface PokedexAdditionDao {
    @Upsert
    suspend fun upsert(pokedexAdditionEntities: List<PokedexAdditionEntity>)

    @Query("SELECT * FROM pokedex_addition WHERE versionGroupId = :versionGroupId")
    suspend fun get(versionGroupId: Int): PokedexAdditionEntity?

    @Query("DELETE FROM pokedex_addition")
    suspend fun delete()
}