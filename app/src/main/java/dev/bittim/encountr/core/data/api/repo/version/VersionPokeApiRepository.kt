/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionPokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 20:18
 */

package dev.bittim.encountr.core.data.api.repo.version

import androidx.room.withTransaction
import androidx.work.WorkManager
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionStub
import dev.bittim.encountr.core.data.api.local.entity.base.versionGroup.VersionGroupStub
import dev.bittim.encountr.core.data.api.worker.ApiSyncWorker
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepository
import dev.bittim.encountr.core.domain.model.api.version.Version
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
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

    override fun getIds(): Flow<List<Int>> {
        queueWorker()
        return apiDatabase.versionDao().getIds().distinctUntilChanged().flowOn(Dispatchers.IO)
    }

    // endregion:   -- Get
    // region:      -- Refresh

    override suspend fun refresh(id: Int) {
        val raw = pokeApi.getVersion(id)
        val versionAddition = definitionRepository.getVersionAdditions(id)
        val isIgnored = definitionRepository.isVersionIgnored(id)

        if (versionAddition == null) return

        val stub = VersionStub(raw.id, raw.versionGroup.id, isIgnored)
        val detail = VersionDetailEntity.fromApi(raw, versionAddition)
        val localizedNames =
            raw.names.map { VersionLocalizedNameEntity.fromApi(id, it) }

        apiDatabase.withTransaction {
            val versionGroup = apiDatabase.versionGroupDao().get(raw.versionGroup.id).firstOrNull()
            if (versionGroup == null) {
                apiDatabase.versionGroupDao().upsertStub(
                    VersionGroupStub(raw.versionGroup.id, null)
                )
            }

            apiDatabase.versionDao().upsertStub(stub)
            apiDatabase.versionDao().upsertDetail(detail)
            apiDatabase.versionDao().upsertLocalizedName(localizedNames)
        }
    }

    override suspend fun refresh() {
        val count = pokeApi.getVersionList(0, 1).count
        val raw = pokeApi.getVersionList(0, count).results
        val stubs =
            raw.map { VersionStub(it.id, null, definitionRepository.isVersionIgnored(it.id)) }

        apiDatabase.withTransaction {
            apiDatabase.versionDao().upsertStub(stubs)
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