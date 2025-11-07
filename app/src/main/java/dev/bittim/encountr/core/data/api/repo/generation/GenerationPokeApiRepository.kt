/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GenerationPokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.repo.generation

import androidx.work.WorkManager
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationEntity
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.versionGroup.VersionGroupEntity
import dev.bittim.encountr.core.data.api.worker.ApiSyncWorker
import dev.bittim.encountr.core.domain.model.api.Handle
import dev.bittim.encountr.core.domain.model.api.version.Generation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
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

    override fun get(): Flow<List<Generation?>> {
        queueWorker()
        return apiDatabase.generationDao().get().distinctUntilChanged().map { list ->
            list.map { it.toModel() }
        }
    }

    // endregion:   -- Get
    // region:      -- Upsert

    override suspend fun refresh(id: Int): Generation? {
        val rawGeneration = pokeApi.getGeneration(id)
        val generationEntity = GenerationEntity.fromApi(rawGeneration)
        val generationLocalizedNameEntities = rawGeneration.names.map { name ->
            GenerationLocalizedNameEntity.fromApi(id, name)
        }

        apiDatabase.generationDao().upsert(generationEntity, generationLocalizedNameEntities)

        val versionGroups = rawGeneration.versionGroups.map { it.id }
        versionGroups.forEach {
            apiDatabase.versionGroupDao().insert(VersionGroupEntity.empty(it, id))
        }

        return generationEntity.toModel(
            generationLocalizedNameEntities.map { it.toModel() },
            versionGroups.map { Handle(it) }
        )
    }

    override suspend fun refresh(): List<Generation> {
        val count = pokeApi.getGenerationList(0, 1).count
        val rawGenList = pokeApi.getGenerationList(0, count).results

        return coroutineScope {
            rawGenList.map {
                async(Dispatchers.IO) {
                    refresh(it.id)
                }
            }.awaitAll().filterNotNull()
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