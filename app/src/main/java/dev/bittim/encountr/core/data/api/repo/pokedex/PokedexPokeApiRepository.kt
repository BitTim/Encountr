/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexPokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.repo.pokedex

import androidx.work.WorkManager
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.local.entity.base.ExpirableEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.junction.PokedexPokemonJunction
import dev.bittim.encountr.core.data.api.worker.ApiSyncWorker
import dev.bittim.encountr.core.domain.model.api.Handle
import dev.bittim.encountr.core.domain.model.api.pokedex.Pokedex
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class PokedexPokeApiRepository(
    private val apiDatabase: ApiDatabase,
    private val pokeApi: PokeApi,
    private val workManager: WorkManager,
) : PokedexRepository {
    // region:      -- Get

    override fun get(id: Int): Flow<Pokedex?> {
        queueWorker(id)
        return apiDatabase.pokedexDao().get(id).distinctUntilChanged().map {
            it?.toModel()
        }.flowOn(Dispatchers.IO)
    }

    override fun get(): Flow<List<Pokedex>> {
        queueWorker()
        return apiDatabase.pokedexDao().get().distinctUntilChanged().map { list ->
            list.map { it.toModel() }
        }.flowOn(Dispatchers.IO)
    }

    // endregion:   -- Get
    // region:      -- Refresh

    override suspend fun refresh(id: Int): Pokedex {
        val rawPokedex = pokeApi.getPokedex(id)
        val pokedexEntity = PokedexEntity.fromApi(rawPokedex)
        val pokedexLocalizedNameEntities = rawPokedex.names.map {
            PokedexLocalizedNameEntity.fromApi(id, it)
        }
        val junctions = rawPokedex.pokemonEntries.map {
            PokedexPokemonJunction(
                id,
                it.pokemonSpecies.id,
                it.entryNumber,
                ExpirableEntity.calcExpiryTime()
            )
        }

        apiDatabase.pokedexDao().upsert(pokedexEntity, pokedexLocalizedNameEntities, junctions)
        return pokedexEntity.toModel(
            pokedexLocalizedNameEntities.map { it.toModel() },
            rawPokedex.pokemonEntries.map { Handle(it.pokemonSpecies.id) }
        )
    }

    override suspend fun refresh(): List<Pokedex> {
        val count = pokeApi.getPokedexList(0, 1).count
        val rawPokedexList = pokeApi.getPokedexList(0, count).results

        return coroutineScope {
            rawPokedexList.map {
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
        type = Pokedex::class.simpleName,
        id = id
    )

    // endregion:   -- Worker
}