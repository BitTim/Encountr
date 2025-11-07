/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.dao.base

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.junction.PokedexPokemonJunction
import dev.bittim.encountr.core.data.api.local.entity.reltaion.pokedex.PokedexFull
import kotlinx.coroutines.flow.Flow

@Dao
interface PokedexDao {
    // region:      -- Create

    @Transaction
    @Upsert
    suspend fun upsert(
        pokedexEntity: PokedexEntity,
        pokedexLocalizedNameEntities: List<PokedexLocalizedNameEntity>,
        pokedexPokemonJunctions: List<PokedexPokemonJunction>,
    )

    @Transaction
    @Upsert
    suspend fun upsert(
        pokedexEntities: List<PokedexEntity>,
        pokedexLocalizedNameEntities: List<PokedexLocalizedNameEntity>,
        pokedexPokemonJunctions: List<PokedexPokemonJunction>,
    )

    // endregion:   -- Create
    // region:      -- Read

    @Transaction
    @Query("SELECT * FROM pokedex WHERE id = :id")
    fun get(id: Int): Flow<PokedexFull?>

    @Transaction
    @Query("SELECT * FROM pokedex")
    fun get(): Flow<List<PokedexFull>>

    // endregion:   -- Read
    // region:      -- Delete

    @Transaction
    @Query("DELETE FROM pokedex")
    suspend fun delete()

    // endregion:   -- Delete
}