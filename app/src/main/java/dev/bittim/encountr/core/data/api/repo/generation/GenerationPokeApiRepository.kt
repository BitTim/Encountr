/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GenerationPokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   23.11.25, 17:39
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
import dev.bittim.encountr.core.domain.model.api.generation.Generation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class GenerationPokeApiRepository(
    workManager: WorkManager,
    private val apiDatabase: ApiDatabase,
    private val pokeApi: PokeApi,
) : GenerationRepository(workManager, apiDatabase) {
    // region:      -- Get

    override fun get(id: Int): Flow<Generation?> {
        return apiDatabase.generationDao().get(id)
            .onStart { refresh(id) }
            .distinctUntilChanged()
            .map { it?.toModel() }
            .flowOn(Dispatchers.IO)
    }

    override fun getIds(): Flow<List<Int>> {
        return apiDatabase.generationDao().getIds()
            .onStart { refresh() }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
    }

    override fun getVersionGroupIds(id: Int): Flow<List<Int>> {
        return apiDatabase.generationDao().getVersionGroupIds(id)
            .onStart { refresh(id) }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
    }

    // endregion:   -- Get
    // region:      -- Fetch

    override suspend fun fetch(id: Int) {
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

    override suspend fun fetch() {
        val count = pokeApi.getGenerationList(0, 1).count
        val raw = pokeApi.getGenerationList(0, count).results
        val stubs = raw.map { GenerationStub(it.id) }

        apiDatabase.withTransaction {
            apiDatabase.generationDao().upsertStub(stubs)
        }
    }

    // endregion:   -- Fetch
}