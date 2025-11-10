/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexPokemonJunctionDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.dao.junction

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.bittim.encountr.core.data.api.local.entity.junction.PokedexPokemonJunction
import kotlinx.coroutines.flow.Flow

@Dao
interface PokedexPokemonJunctionDao {
    // region:      -- Create / Update

    @Upsert
    suspend fun upsert(pokedexPokemonJunction: PokedexPokemonJunction)

    @Upsert
    suspend fun upsert(pokedexPokemonJunctions: List<PokedexPokemonJunction>)

    // endregion:   -- Create / Update
    // region:      -- Read

    @Query("SELECT * FROM pokedex_pokemon_junction WHERE pokemonId = :pokemonId")
    fun getByPokemonOverview(pokemonId: Int): Flow<List<PokedexPokemonJunction>>

    @Query("SELECT * FROM pokedex_pokemon_junction WHERE pokedexId = :pokedexId")
    fun getByPokedex(pokedexId: Int): Flow<List<PokedexPokemonJunction>>

    // endregion:   -- Read
    // region:      -- Delete

    @Query("DELETE FROM pokedex_pokemon_junction")
    suspend fun delete()

    // endregion:   -- Delete
}