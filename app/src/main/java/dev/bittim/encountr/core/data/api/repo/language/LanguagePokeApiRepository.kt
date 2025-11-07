/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LanguagePokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.repo.language

import androidx.work.WorkManager
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.local.entity.base.language.LanguageEntity
import dev.bittim.encountr.core.data.api.worker.ApiSyncWorker
import dev.bittim.encountr.core.domain.model.api.language.Language
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
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
        return apiDatabase.languageDao().get(id).distinctUntilChanged().map { it?.toModel() }
            .flowOn(Dispatchers.IO)
    }

    override fun get(): Flow<List<Language?>> {
        queueWorker()
        return apiDatabase.languageDao().get().distinctUntilChanged().map { list ->
            list.map { it.toModel() }
        }.flowOn(Dispatchers.IO)
    }

    // endregion:   -- Get
    // region:      -- Refresh

    override suspend fun refresh(id: Int): Language? {
        apiDatabase.languageDao().insert(LanguageEntity.empty(id))

        val rawLanguage = pokeApi.getLanguage(id)
        val languageEntity = LanguageEntity.fromApi(rawLanguage)

        apiDatabase.languageDao().upsert(languageEntity)
        return languageEntity.toModel()
    }

    override suspend fun refresh(): List<Language> {
        val count = pokeApi.getLanguageList(0, 1).count
        val rawLangList = pokeApi.getLanguageList(0, count).results

        return coroutineScope {
            rawLangList.map {
                async(Dispatchers.IO) {
                    refresh(it.id)
                }
            }.awaitAll().filterNotNull()
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