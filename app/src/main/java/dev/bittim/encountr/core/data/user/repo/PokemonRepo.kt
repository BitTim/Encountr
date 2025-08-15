/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonRepo.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 10:53
 */

package dev.bittim.encountr.core.data.user.repo

import co.pokeapi.pokekotlin.model.PokemonVariety
import dev.bittim.encountr.core.domain.model.user.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface PokemonRepo {
    // region:      -- Create

    suspend fun create(save: Uuid, pokemon: PokemonVariety, caught: Boolean = false): Pokemon

    // endregion:   -- Create
    // region:      -- Read

    fun get(save: Uuid, pokemon: PokemonVariety): Flow<Pokemon?>
    fun getAll(save: Uuid): Flow<List<Pokemon>>

    // endregion:   -- Read
    // region:      -- Update

    suspend fun update(save: Uuid, pokemon: PokemonVariety, caught: Boolean)

    // endregion:   -- Update
    // region:      -- Delete

    suspend fun delete(save: Uuid, pokemon: PokemonVariety)
    suspend fun deleteAll(save: Uuid)

    // endregion:   -- Delete
}