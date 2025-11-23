/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TypePokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   23.11.25, 17:41
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
import dev.bittim.encountr.core.domain.model.api.type.Type
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class TypePokeApiRepository(
    workManager: WorkManager,
    private val apiDatabase: ApiDatabase,
    private val pokeApi: PokeApi,
    private val httpClient: HttpClient // TODO: Remove when pokekotlin PR is merged
) : TypeRepository(workManager, apiDatabase) {
    // region:      -- Get

    override fun get(id: Int): Flow<Type?> {
        return apiDatabase.typeDao().get(id)
            .onStart { refresh(id) }
            .distinctUntilChanged()
            .map { it?.toModel() }
            .flowOn(Dispatchers.IO)
    }

    override fun getIds(): Flow<List<Int>> {
        return apiDatabase.typeDao().getIds()
            .onStart { refresh() }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
    }

    // endregion:   -- Get
    // region:      -- Fetch

    override suspend fun fetch(id: Int) {
        //val raw = pokeApi.getType(id)
        val raw = httpClient.get("https://pokeapi.co/api/v2/type/$id")
            .body<TypeDto>() // TODO: Remove when pokekotlin PR is merged

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

    override suspend fun fetch() {
        val count = pokeApi.getTypeList(0, 1).count
        val raw = pokeApi.getTypeList(0, count).results
        val stubs = raw.map { TypeStub(it.id) }

        apiDatabase.withTransaction {
            apiDatabase.typeDao().upsertStub(stubs)
        }
    }

    // endregion:   -- Fetch
}