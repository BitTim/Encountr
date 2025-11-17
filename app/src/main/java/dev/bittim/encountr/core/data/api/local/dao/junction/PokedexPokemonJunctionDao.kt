/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexPokemonJunctionDao.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 20:23
 */

package dev.bittim.encountr.core.data.api.local.dao.junction

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.bittim.encountr.core.data.api.local.entity.junction.PokedexPokemonJunction
import dev.bittim.encountr.core.data.api.local.entity.reltaion.pokemon.PartialPokedexEntry
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

    @Query("SELECT pokedexId, entryNumber FROM pokedex_pokemon_junction WHERE pokemonId = :pokemonId")
    fun getByPokemon(pokemonId: Int): Flow<List<PartialPokedexEntry>>

    @Query("SELECT pokemonId FROM pokedex_pokemon_junction WHERE pokedexId = :pokedexId ORDER BY entryNumber ASC")
    fun getByPokedex(pokedexId: Int): Flow<List<Int>>

    // endregion:   -- Read
    // region:      -- Delete

    @Query("DELETE FROM pokedex_pokemon_junction")
    suspend fun delete()

    // endregion:   -- Delete
}