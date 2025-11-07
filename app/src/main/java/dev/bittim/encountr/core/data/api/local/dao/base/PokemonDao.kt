/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.dao.base

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonSpritesEntity
import dev.bittim.encountr.core.data.api.local.entity.junction.PokemonTypeJunction
import dev.bittim.encountr.core.data.api.local.entity.reltaion.pokemon.PokemonFull
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    // region:      -- Create / Update

    @Transaction
    @Upsert
    suspend fun upsert(
        pokemonEntity: PokemonEntity,
        pokemonLocalizedNameEntities: List<PokemonLocalizedNameEntity>,
        pokemonSpritesEntities: List<PokemonSpritesEntity>,
        pokemonTypeJunctions: List<PokemonTypeJunction>
    )

    @Transaction
    @Upsert
    suspend fun upsert(
        pokemonEntities: List<PokemonEntity>,
        pokemonLocalizedNameEntities: List<PokemonLocalizedNameEntity>,
        pokemonSpritesEntities: List<PokemonSpritesEntity>,
        pokemonTypeJunctions: List<PokemonTypeJunction>
    )

    // endregion:   -- Create / Update
    // region:      -- Read

    @Transaction
    @Query("SELECT * FROM pokemon WHERE id = :id")
    fun get(id: Int): Flow<PokemonFull?>

    @Transaction
    @Query("SELECT * FROM pokemon")
    fun get(): Flow<List<PokemonFull>>

    // endregion:   -- Read
    // region:      -- Delete

    @Query("DELETE FROM pokemon")
    suspend fun delete()

    // endregion:   -- Delete
}