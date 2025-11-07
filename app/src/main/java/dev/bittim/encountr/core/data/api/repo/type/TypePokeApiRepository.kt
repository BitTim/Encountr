/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TypePokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.repo.type

import androidx.work.WorkManager
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeSpriteEntity
import dev.bittim.encountr.core.data.api.worker.ApiSyncWorker
import dev.bittim.encountr.core.domain.model.api.type.Type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class TypePokeApiRepository(
    private val apiDatabase: ApiDatabase,
    private val pokeApi: PokeApi,
    private val workManager: WorkManager
) : TypeRepository {
    // region:      -- Get

    override fun get(id: Int): Flow<Type?> {
        queueWorker(id)
        return apiDatabase.typeDao().get(id).distinctUntilChanged().map {
            it?.toModel()
        }.flowOn(Dispatchers.IO)
    }

    override fun get(): Flow<List<Type>> {
        queueWorker()
        return apiDatabase.typeDao().get().distinctUntilChanged().map { list ->
            list.map { it.toModel() }
        }.flowOn(Dispatchers.IO)
    }

    // endregion:   -- Get
    // region:      -- Refresh

    override suspend fun refresh(id: Int): Type {
        val rawType = pokeApi.getType(id)
        val typeEntity = TypeEntity.fromApi(rawType)
        val typeLocalizedNameEntities =
            rawType.names.map { TypeLocalizedNameEntity.fromApi(id, it) }
        val typeSpriteEntities = TypeSpriteEntity.fromApi(id, rawType.sprites)

        apiDatabase.typeDao().upsert(typeEntity, typeLocalizedNameEntities, typeSpriteEntities)
        return typeEntity.toModel(
            localizedNames = typeLocalizedNameEntities.map { it.toModel() },
            typeSprites = typeSpriteEntities.map { it.toModel() }
        )
    }

    override suspend fun refresh(): List<Type> {
        val count = pokeApi.getTypeList(0, 1).count
        val rawTypeList = pokeApi.getTypeList(0, count).results

        return coroutineScope {
            rawTypeList.map {
                async(Dispatchers.IO) {
                    refresh(it.id)
                }
            }.awaitAll()
        }
    }

    // endregion:   -- Refresh
    // region:      -- Worker

    override fun queueWorker(id: Int?) = ApiSyncWorker.enqueue(
        workManager = workManager,
        type = Type::class.simpleName,
        id = id
    )

    // endregion:   -- Worker
}