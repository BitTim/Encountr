/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TeamRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.user.repo

import dev.bittim.encountr.core.domain.model.user.Team
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface TeamRepository {
    // region:      -- Create

    suspend fun create(save: Uuid, name: String): Team

    // endregion:   -- Create
    // region:      -- Read

    fun get(save: Uuid, id: Uuid): Flow<Team?>
    fun getAll(save: Uuid): Flow<List<Team>>

    // endregion:   -- Read
    // region:      -- Update

    suspend fun update(save: Uuid, id: Uuid, name: String)
    suspend fun addPokemon(id: Uuid, pokemonId: Int)
    suspend fun removePokemon(id: Uuid, pokemonId: Int)

    // endregion:   -- Update
    // region:      -- Delete

    suspend fun delete(save: Uuid, id: Uuid)
    suspend fun deleteAll(save: Uuid)

    // endregion:   -- Delete
}