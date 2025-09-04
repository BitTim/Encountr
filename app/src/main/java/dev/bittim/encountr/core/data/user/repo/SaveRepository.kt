/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SaveRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   04.09.25, 23:26
 */

package dev.bittim.encountr.core.data.user.repo

import dev.bittim.encountr.core.domain.model.user.Save
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface SaveRepository {
    // region:      -- Create

    suspend fun create(name: String, gameId: Int): Save

    // endregion:   -- Create
    // region:      -- Read

    fun get(id: Uuid): Flow<Save?>
    fun getAll(): Flow<List<Save>>

    // endregion:   -- Read
    // region:      -- Update

    suspend fun update(id: Uuid, name: String, gameId: Int)
    suspend fun updateName(id: Uuid, name: String)
    suspend fun updateGame(id: Uuid, gameId: Int)

    // endregion:   -- Update
    // region:      -- Delete

    suspend fun delete(id: Uuid)
    suspend fun deleteAll()

    // endregion:   -- Delete
}