/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SaveRepo.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 02:35
 */

package dev.bittim.encountr.core.data.user.repo

import dev.bittim.encountr.core.domain.model.user.Save
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
interface SaveRepo {
    fun create(name: String, game: String): Save

    fun get(id: Uuid): Flow<Save?>
    fun getAll(): Flow<List<Save>>
}