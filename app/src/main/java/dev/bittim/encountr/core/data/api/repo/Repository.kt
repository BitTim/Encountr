/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Repository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.repo

import kotlinx.coroutines.flow.Flow

interface Repository<T> {
    fun get(id: Int): Flow<T?>
    fun getIds(): Flow<List<Int>>

    suspend fun refresh(id: Int)
    suspend fun refresh()

    fun queueWorker(id: Int? = null)
}