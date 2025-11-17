/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionGroupPokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 02:31
 */

package dev.bittim.encountr.core.data.api.repo.versionGroup

import androidx.room.withTransaction
import androidx.work.WorkManager
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexStub
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionStub
import dev.bittim.encountr.core.data.api.local.entity.base.versionGroup.VersionGroupDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.versionGroup.VersionGroupStub
import dev.bittim.encountr.core.data.api.local.entity.junction.VersionGroupPokedexJunction
import dev.bittim.encountr.core.data.api.worker.ApiSyncWorker
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepository
import dev.bittim.encountr.core.domain.model.api.versionGroup.VersionGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class VersionGroupPokeApiRepository(
    private val apiDatabase: ApiDatabase,
    private val pokeApi: PokeApi,
    private val definitionRepository: DefinitionRepository,
    private val workManager: WorkManager,
) : VersionGroupRepository {
    // region:      -- Get

    override fun get(id: Int): Flow<VersionGroup?> {
        queueWorker(id)
        return apiDatabase.versionGroupDao().get(id).distinctUntilChanged()
            .map {
                it?.toModel()
            }.flowOn(Dispatchers.IO)
    }

    override fun getIds(): Flow<List<Int>> {
        queueWorker()
        return apiDatabase.versionGroupDao().getIds().distinctUntilChanged().flowOn(Dispatchers.IO)
    }

    override fun getVersionIds(id: Int): Flow<List<Int>> {
        queueWorker(id)
        return apiDatabase.versionGroupDao().getVersionIds(id).distinctUntilChanged()
            .flowOn(Dispatchers.IO)
    }

    override fun getPokedexIds(id: Int): Flow<List<Int>> {
        queueWorker(id)
        return apiDatabase.versionGroupPokedexJunctionDao().getPokedexIdsByVersionGroup(id)
            .distinctUntilChanged().flowOn(Dispatchers.IO)
    }

    // endregion:   -- Get
    // region:      -- Refresh

    override suspend fun refresh(id: Int) {
        val linkedPokedexes = definitionRepository.getPokedexAdditions(id) ?: emptyList()

        val raw = pokeApi.getVersionGroup(id)
        val stub = VersionGroupStub(raw.id, raw.generation.id)
        val detail = VersionGroupDetailEntity.fromApi(raw)

        val pokedexes = raw.pokedexes.map { it.id } + linkedPokedexes
        val pokedexStubs = pokedexes.map { PokedexStub(it) }
        val versionGroupPokedexJunctions = pokedexes.map {
            VersionGroupPokedexJunction(
                versionGroupId = id,
                pokedexId = it
            )
        }

        val versionStubs = raw.versions.map {
            VersionStub(
                it.id,
                id,
                definitionRepository.isVersionIgnored(it.id)
            )
        }

        apiDatabase.withTransaction {
            apiDatabase.versionGroupDao().upsertStub(stub)
            apiDatabase.versionGroupDao().upsertDetail(detail)

            apiDatabase.pokedexDao().upsertStub(pokedexStubs)
            apiDatabase.versionGroupPokedexJunctionDao().upsert(versionGroupPokedexJunctions)

            apiDatabase.versionDao().upsertStub(versionStubs)
        }
    }

    override suspend fun refresh() {
        val count = pokeApi.getVersionGroupList(0, 1).count
        val raw = pokeApi.getVersionGroupList(0, count).results
        val stubs = raw.map { VersionGroupStub(it.id, null) }

        apiDatabase.withTransaction {
            apiDatabase.versionGroupDao().upsertStub(stubs)
        }
    }

    // endregion:   -- Refresh
    // region:      -- Worker

    override fun queueWorker(id: Int?) = ApiSyncWorker.enqueue(
        workManager = workManager,
        type = VersionGroup::class.simpleName,
        id = id
    )

    // endregion:   -- Worker
}