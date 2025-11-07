/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionGroupPokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.repo.versionGroup

import android.util.Log
import androidx.work.WorkManager
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.local.entity.base.ExpirableEntity
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionEntity
import dev.bittim.encountr.core.data.api.local.entity.base.versionGroup.VersionGroupEntity
import dev.bittim.encountr.core.data.api.local.entity.junction.VersionGroupPokedexJunction
import dev.bittim.encountr.core.data.api.worker.ApiSyncWorker
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepository
import dev.bittim.encountr.core.domain.model.api.Handle
import dev.bittim.encountr.core.domain.model.api.version.VersionGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
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
        Log.d("VersionGroupRepo", "Getting version group $id")
        queueWorker(id)
        Log.d("VersionGroupRepo", "Version group $id queued")
        val flow = apiDatabase.versionGroupDao().get(id).distinctUntilChanged().map {
            val model = it?.toModel()
            Log.d("VersionGroupRepo", "Mapped $id to $model")
            model
        }.flowOn(Dispatchers.IO)
        Log.d("VersionGroupRepo", "Version group $id mapped")
        return flow
    }

    override fun get(): Flow<List<VersionGroup?>> {
        queueWorker()
        return apiDatabase.versionGroupDao().get().distinctUntilChanged().map { list ->
            list.map { it.toModel() }
        }.flowOn(Dispatchers.IO)
    }

    // endregion:   -- Get
    // region:      -- Refresh

    override suspend fun refresh(id: Int): VersionGroup? {
        val linkedPokedexes = definitionRepository.getPokedexAdditions(id) ?: emptyList()

        val rawVersionGroup = pokeApi.getVersionGroup(id)
        val versionGroupEntity = VersionGroupEntity.fromApi(rawVersionGroup)

        val pokedexes = rawVersionGroup.pokedexes.map { it.id } + linkedPokedexes
        val versionGroupPokedexJunctions = pokedexes.map {
            VersionGroupPokedexJunction(
                versionGroupId = id,
                pokedexId = it,
                expiresAt = ExpirableEntity.calcExpiryTime()
            )
        }

        apiDatabase.versionGroupDao().upsert(versionGroupEntity, versionGroupPokedexJunctions)

        val versions = rawVersionGroup.versions.map { it.id }
            .filter { !definitionRepository.isVersionIgnored(it) }
        versions.forEach {
            apiDatabase.versionDao().insert(VersionEntity.empty(it, id))
        }

        return versionGroupEntity.toModel(
            versions = versions.map { Handle(it) },
            pokedexes = rawVersionGroup.pokedexes.map { Handle(it.id) }
        )
    }

    override suspend fun refresh(): List<VersionGroup> {
        val count = pokeApi.getVersionGroupList(0, 1).count
        val rawVersionGroupList = pokeApi.getVersionGroupList(0, count).results

        return coroutineScope {
            rawVersionGroupList.map {
                async {
                    refresh(it.id)
                }
            }.awaitAll().filterNotNull()
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