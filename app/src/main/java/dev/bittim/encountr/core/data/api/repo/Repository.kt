/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Repository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   23.11.25, 17:46
 */

package dev.bittim.encountr.core.data.api.repo

import androidx.work.WorkManager
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.local.entity.base.CombinedEntity
import dev.bittim.encountr.core.data.api.worker.ApiSyncWorker
import dev.bittim.encountr.core.di.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

abstract class Repository<T>(
    private val workManager: WorkManager,
    private val apiDatabase: ApiDatabase,
) {
    // region:      -- Type

    abstract val type: String

    // endregion:   -- Type
    // region:      -- Get

    abstract fun get(id: Int): Flow<T?>
    abstract fun getIds(): Flow<List<Int>>

    // endregion:   -- Get
    // region:      -- Fetch

    abstract suspend fun fetch(id: Int)
    abstract suspend fun fetch()

    // endregion:   -- Fetch
    // region:      -- Refresh

    suspend fun refresh(id: Int? = null, force: Boolean = false) {
        if (id == null) {
            // No ID provided -> Refresh all entities of this type
            val ids = apiDatabase.getIdsOf(type).firstOrNull() ?: emptyList()
            // Cancel refresh if IDs are present
            if (!isExpired(ids, force)) return
        } else {
            // ID provided -> Refresh this entity
            val entity = apiDatabase.getOf(type, id).firstOrNull()
            // Cancel refresh is not expired
            if (!isExpired(entity, force)) return
        }

        ApiSyncWorker.enqueue(workManager, type, id)
    }

    // endregion:   -- Refresh
    // region:      -- Expiry

    @OptIn(ExperimentalTime::class)
    private fun isExpired(entity: CombinedEntity?, force: Boolean = false): Boolean {
        if (entity == null) return true

        val expirationDuration = Constants.API_EXPIRATION_DAYS.days
        val updatedAt = if (entity.detail == null) 0 else entity.stub.updatedAt
        val expirationTime = Instant.fromEpochSeconds(updatedAt) + expirationDuration
        val isExpired = expirationTime < Clock.System.now()

        return isExpired || force
    }

    @OptIn(ExperimentalTime::class)
    private fun isExpired(ids: List<Int>, force: Boolean = false): Boolean {
        return ids.isEmpty() || force
    }

    // endregion:   -- Expiry
}