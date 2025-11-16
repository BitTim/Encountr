/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TypePokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.11.25, 02:32
 */

package dev.bittim.encountr.core.data.api.repo.type

import androidx.room.withTransaction
import androidx.work.WorkManager
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeSpriteEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeStub
import dev.bittim.encountr.core.data.api.worker.ApiSyncWorker
import dev.bittim.encountr.core.domain.model.api.type.Type
import kotlinx.coroutines.Dispatchers
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

    override fun getIds(): Flow<List<Int>> {
        queueWorker()
        return apiDatabase.typeDao().getIds().distinctUntilChanged().flowOn(Dispatchers.IO)
    }

    // endregion:   -- Get
    // region:      -- Refresh

    override suspend fun refresh(id: Int) {
        val raw = pokeApi.getType(id)

        val stub = TypeStub(raw.id)
        val detail = TypeDetailEntity.fromApi(raw)
        val localizedNames = raw.names.map { TypeLocalizedNameEntity.fromApi(id, it) }
        val sprites = TypeSpriteEntity.fromApi(id, raw.sprites)

        apiDatabase.withTransaction {
            apiDatabase.typeDao().upsertStub(stub)
            apiDatabase.typeDao().upsertDetail(detail)
            apiDatabase.typeDao().upsertLocalizedName(localizedNames)
            apiDatabase.typeDao().upsertSprite(sprites)
        }
    }

    override suspend fun refresh() {
        val count = pokeApi.getTypeList(0, 1).count
        val raw = pokeApi.getTypeList(0, count).results
        val stubs = raw.map { TypeStub(it.id) }

        apiDatabase.withTransaction {
            apiDatabase.typeDao().upsertStub(stubs)
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