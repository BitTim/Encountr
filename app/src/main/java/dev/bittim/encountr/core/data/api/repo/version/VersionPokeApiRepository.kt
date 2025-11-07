/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionPokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.repo.version

import androidx.work.WorkManager
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionEntity
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionLocalizedNameEntity
import dev.bittim.encountr.core.data.api.worker.ApiSyncWorker
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepository
import dev.bittim.encountr.core.domain.model.api.version.Version
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class VersionPokeApiRepository(
    private val apiDatabase: ApiDatabase,
    private val pokeApi: PokeApi,
    private val definitionRepository: DefinitionRepository,
    private val workManager: WorkManager,
) : VersionRepository {
    // region:      -- Get

    override fun get(id: Int): Flow<Version?> {
        queueWorker(id)
        return apiDatabase.versionDao().get(id).distinctUntilChanged().map {
            it?.toModel()
        }.flowOn(Dispatchers.IO)
    }

    override fun get(): Flow<List<Version?>> {
        queueWorker()
        return apiDatabase.versionDao().get().distinctUntilChanged().map { list ->
            list.map { it.toModel() }
        }.flowOn(Dispatchers.IO)
    }

    // endregion:   -- Get
    // region:      -- Refresh

    override suspend fun refresh(id: Int): Version? {
        if (definitionRepository.isVersionIgnored(id)) return null
        val rawVersion = pokeApi.getVersion(id)
        val imageUrl = definitionRepository.getVersionIcon(id)

        val versionEntity = VersionEntity.fromApi(rawVersion, imageUrl)
        val versionLocalizedNameEntities =
            rawVersion.names.map { VersionLocalizedNameEntity.fromApi(id, it) }

        apiDatabase.versionDao().upsert(versionEntity, versionLocalizedNameEntities)
        return versionEntity.toModel(
            versionLocalizedNameEntities.map { it.toModel() }
        )
    }

    override suspend fun refresh(): List<Version> {
        val count = pokeApi.getVersionList(0, 1).count
        val rawVersionList = pokeApi.getVersionList(0, count).results

        return coroutineScope {
            rawVersionList.map {
                async(Dispatchers.IO) {
                    refresh(it.id)
                }
            }.awaitAll().filterNotNull()
        }
    }

    // endregion:   -- Refresh
    // region:      -- Worker

    override fun queueWorker(id: Int?) = ApiSyncWorker.enqueue(
        workManager = workManager,
        type = Version::class.simpleName,
        id = id
    )

    // endregion:   -- Worker
}