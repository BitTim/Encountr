/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.dao.base

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonSpritesEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonStub
import dev.bittim.encountr.core.data.api.local.entity.reltaion.pokemon.PokemonFull
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    // region:      -- Create / Update

    @Upsert
    suspend fun upsertStub(pokemonStub: PokemonStub)

    @Upsert
    suspend fun upsertStub(pokemonStubs: List<PokemonStub>)

    @Upsert
    suspend fun upsertDetail(pokemonDetailEntity: PokemonDetailEntity)

    @Upsert
    suspend fun upsertDetail(pokemonDetailEntities: List<PokemonDetailEntity>)

    @Upsert
    suspend fun upsertLocalizedName(pokemonLocalizedNameEntity: PokemonLocalizedNameEntity)

    @Upsert
    suspend fun upsertLocalizedName(pokemonLocalizedNameEntities: List<PokemonLocalizedNameEntity>)

    @Upsert
    suspend fun upsertSprite(pokemonSpritesEntity: PokemonSpritesEntity)

    @Upsert
    suspend fun upsertSprite(pokemonSpritesEntities: List<PokemonSpritesEntity>)

    // endregion:   -- Create / Update
    // region:      -- Read

    @Transaction
    @Query("SELECT * FROM pokemon_stub WHERE id = :id")
    fun get(id: Int): Flow<PokemonFull?>

    @Query("SELECT id FROM pokemon_stub")
    fun getIds(): Flow<List<Int>>

    // endregion:   -- Read
    // region:      -- Delete

    @Query("DELETE FROM pokemon_stub")
    suspend fun delete()

    // endregion:   -- Delete
}