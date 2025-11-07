/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Repository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.repo

import kotlinx.coroutines.flow.Flow

interface Repository<T> {
    fun get(id: Int): Flow<T?>
    fun get(): Flow<List<T?>>

    suspend fun refresh(id: Int): T?
    suspend fun refresh(): List<T>

    fun queueWorker(id: Int? = null)
}