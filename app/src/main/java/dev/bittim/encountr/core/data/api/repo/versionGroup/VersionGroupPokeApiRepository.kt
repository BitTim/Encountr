/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionGroupPokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   23.11.25, 17:44
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
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepository
import dev.bittim.encountr.core.domain.model.api.versionGroup.VersionGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class VersionGroupPokeApiRepository(
    workManager: WorkManager,
    private val apiDatabase: ApiDatabase,
    private val pokeApi: PokeApi,
    private val definitionRepository: DefinitionRepository,
) : VersionGroupRepository(workManager, apiDatabase) {
    // region:      -- Get

    override fun get(id: Int): Flow<VersionGroup?> {
        return apiDatabase.versionGroupDao().get(id)
            .onStart { refresh(id) }
            .distinctUntilChanged()
            .map { it?.toModel() }
            .flowOn(Dispatchers.IO)
    }

    override fun getIds(): Flow<List<Int>> {
        return apiDatabase.versionGroupDao().getIds()
            .onStart { refresh() }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
    }

    override fun getVersionIds(id: Int): Flow<List<Int>> {
        return apiDatabase.versionGroupDao().getVersionIds(id)
            .onStart { refresh(id) }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
    }

    override fun getPokedexIds(id: Int): Flow<List<Int>> {
        return apiDatabase.versionGroupPokedexJunctionDao().getPokedexIdsByVersionGroup(id)
            .onStart { refresh(id) }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
    }

    // endregion:   -- Get
    // region:      -- Fetch

    override suspend fun fetch(id: Int) {
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

    override suspend fun fetch() {
        val count = pokeApi.getVersionGroupList(0, 1).count
        val raw = pokeApi.getVersionGroupList(0, count).results
        val stubs = raw.map { VersionGroupStub(it.id, null) }

        apiDatabase.withTransaction {
            apiDatabase.versionGroupDao().upsertStub(stubs)
        }
    }

    // endregion:   -- Fetch
}