/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexPokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.11.25, 02:32
 */

package dev.bittim.encountr.core.data.api.repo.pokedex

import androidx.room.withTransaction
import androidx.work.WorkManager
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexStub
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonStub
import dev.bittim.encountr.core.data.api.local.entity.junction.PokedexPokemonJunction
import dev.bittim.encountr.core.data.api.worker.ApiSyncWorker
import dev.bittim.encountr.core.domain.model.api.pokedex.Pokedex
import kotlinx.coroutines.Dispatchers
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

    override fun getIds(): Flow<List<Int>> {
        queueWorker()
        return apiDatabase.pokedexDao().getIds().distinctUntilChanged().flowOn(Dispatchers.IO)
    }

    // endregion:   -- Get
    // region:      -- Refresh

    override suspend fun refresh(id: Int) {
        val raw = pokeApi.getPokedex(id)
        val stub = PokedexStub(raw.id)
        val detail = PokedexDetailEntity.fromApi(raw)
        val localizedNames = raw.names.map { PokedexLocalizedNameEntity.fromApi(id, it) }

        val pokemonStubs = raw.pokemonEntries.map { PokemonStub(it.pokemonSpecies.id) }
        val junctions = raw.pokemonEntries.map {
            PokedexPokemonJunction(
                id,
                it.pokemonSpecies.id,
                it.entryNumber
            )
        }

        apiDatabase.withTransaction {
            apiDatabase.pokedexDao().upsertStub(stub)
            apiDatabase.pokedexDao().upsertDetail(detail)
            apiDatabase.pokedexDao().upsertLocalizedName(localizedNames)

            apiDatabase.pokemonDao().upsertStub(pokemonStubs)
            apiDatabase.pokedexPokemonJunctionDao().upsert(junctions)
        }
    }

    override suspend fun refresh() {
        val count = pokeApi.getPokedexList(0, 1).count
        val raw = pokeApi.getPokedexList(0, count).results
        val stubs = raw.map { PokedexStub(it.id) }

        apiDatabase.withTransaction {
            apiDatabase.pokedexDao().upsertStub(stubs)
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