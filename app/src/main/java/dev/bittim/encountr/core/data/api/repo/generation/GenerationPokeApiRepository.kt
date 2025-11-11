/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GenerationPokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.11.25, 15:50
 */

package dev.bittim.encountr.core.data.api.repo.generation

import androidx.room.withTransaction
import androidx.work.WorkManager
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationStub
import dev.bittim.encountr.core.data.api.local.entity.base.versionGroup.VersionGroupStub
import dev.bittim.encountr.core.data.api.worker.ApiSyncWorker
import dev.bittim.encountr.core.domain.model.api.generation.Generation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GenerationPokeApiRepository(
    private val apiDatabase: ApiDatabase,
    private val pokeApi: PokeApi,
    private val workManager: WorkManager,
) : GenerationRepository {
    // region:      -- Get

    override fun get(id: Int): Flow<Generation?> {
        queueWorker(id)
        return apiDatabase.generationDao().get(id).distinctUntilChanged()
            .map { generationEntity ->
                generationEntity?.toModel()
            }.flowOn(Dispatchers.IO)
    }

    override fun getIds(): Flow<List<Int>> {
        queueWorker()
        return apiDatabase.generationDao().getIds().distinctUntilChanged().flowOn(Dispatchers.IO)
    }

    override fun getVersionGroupIds(id: Int): Flow<List<Int>> {
        queueWorker(id)
        return apiDatabase.generationDao().getVersionGroupIds(id).distinctUntilChanged()
            .flowOn(Dispatchers.IO)
    }

    // endregion:   -- Get
    // region:      -- Upsert

    override suspend fun refresh(id: Int) {
        val raw = pokeApi.getGeneration(id)

        val stub = GenerationStub(raw.id)
        val detail = GenerationDetailEntity.fromApi(raw)
        val localizedNames =
            raw.names.map { name -> GenerationLocalizedNameEntity.fromApi(id, name) }

        val versionGroupStubs = raw.versionGroups.map { VersionGroupStub(it.id, id) }

        apiDatabase.withTransaction {
            apiDatabase.generationDao().upsertStub(stub)
            apiDatabase.generationDao().upsertDetail(detail)
            apiDatabase.generationDao().upsertLocalizedName(localizedNames)
            apiDatabase.versionGroupDao().upsertStub(versionGroupStubs)
        }
    }

    override suspend fun refresh() {
        val count = pokeApi.getGenerationList(0, 1).count
        val raw = pokeApi.getGenerationList(0, count).results
        val stubs = raw.map { GenerationStub(it.id) }

        apiDatabase.withTransaction {
            apiDatabase.generationDao().upsertStub(stubs)
        }
    }

    // endregion:   -- Upsert
    // region:      -- Worker

    override fun queueWorker(id: Int?) = ApiSyncWorker.enqueue(
        workManager = workManager,
        type = Generation::class.simpleName,
        id = id
    )

    // endregion:   -- Worker
}