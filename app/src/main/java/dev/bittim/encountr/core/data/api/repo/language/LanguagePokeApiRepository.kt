/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LanguagePokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   13.11.25, 16:21
 */

package dev.bittim.encountr.core.data.api.repo.language

import androidx.room.withTransaction
import androidx.work.WorkManager
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.local.entity.base.language.LanguageDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.language.LanguageStub
import dev.bittim.encountr.core.data.api.worker.ApiSyncWorker
import dev.bittim.encountr.core.domain.model.api.language.Language
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class LanguagePokeApiRepository(
    private val apiDatabase: ApiDatabase,
    private val pokeApi: PokeApi,
    private val workManager: WorkManager
) : LanguageRepository {
    // region:      -- Get

    override fun get(id: Int): Flow<Language?> {
        queueWorker(id)
        return apiDatabase.languageDao().get(id).catch { emit(null) }.distinctUntilChanged()
            .map { it?.toModel() }
            .flowOn(Dispatchers.IO)
    }

    override fun getIds(): Flow<List<Int>> {
        queueWorker()
        return apiDatabase.languageDao().getIds().distinctUntilChanged().flowOn(Dispatchers.IO)
    }

    // endregion:   -- Get
    // region:      -- Refresh

    override suspend fun refresh(id: Int) {
        val raw = pokeApi.getLanguage(id)
        val detail = LanguageDetailEntity.fromApi(raw)
        val stub = LanguageStub(raw.id, detail.localizedName != null)

        apiDatabase.withTransaction {
            apiDatabase.languageDao().upsertStub(stub)
            apiDatabase.languageDao().upsertDetail(detail)
        }
    }

    override suspend fun refresh() {
        val count = pokeApi.getLanguageList(0, 1).count
        val raw = pokeApi.getLanguageList(0, count).results
        val stubs = raw.map { LanguageStub(it.id, true) }

        apiDatabase.withTransaction {
            apiDatabase.languageDao().upsertStub(stubs)
        }
    }

    // endregion:   -- Refresh
    // region:      -- Worker

    override fun queueWorker(id: Int?) = ApiSyncWorker.enqueue(
        workManager = workManager,
        type = Language::class.simpleName,
        id = id
    )

    // endregion:   -- Worker
}