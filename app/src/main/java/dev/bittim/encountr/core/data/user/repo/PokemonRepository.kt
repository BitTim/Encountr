/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   12.09.25, 16:34
 */

package dev.bittim.encountr.core.data.user.repo

import co.pokeapi.pokekotlin.model.PokemonVariety
import dev.bittim.encountr.core.domain.model.user.PokemonState
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface PokemonRepository {
    // region:      -- Create

    suspend fun create(
        save: Uuid,
        pokemon: PokemonVariety,
        caught: Boolean = false,
        shiny: Boolean = false
    ): PokemonState

    // endregion:   -- Create
    // region:      -- Read

    fun get(save: Uuid, pokemon: PokemonVariety): Flow<PokemonState?>
    fun getAll(save: Uuid): Flow<List<PokemonState>>

    // endregion:   -- Read
    // region:      -- Update

    suspend fun update(save: Uuid, pokemon: PokemonVariety, caught: Boolean)

    // endregion:   -- Update
    // region:      -- Delete

    suspend fun delete(save: Uuid, pokemon: PokemonVariety)
    suspend fun deleteAll(save: Uuid)

    // endregion:   -- Delete
}