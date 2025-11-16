/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonPokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.11.25, 02:32
 */

package dev.bittim.encountr.core.data.api.repo.pokemon

import androidx.room.withTransaction
import androidx.work.WorkManager
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonSpritesEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonStub
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeStub
import dev.bittim.encountr.core.data.api.local.entity.junction.PokemonTypeJunction
import dev.bittim.encountr.core.data.api.worker.ApiSyncWorker
import dev.bittim.encountr.core.domain.model.api.pokemon.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class PokemonPokeApiRepository(
    private val apiDatabase: ApiDatabase,
    private val pokeApi: PokeApi,
    private val workManager: WorkManager,
) : PokemonRepository {
    // region:      -- Get

    override fun get(id: Int): Flow<Pokemon?> {
        queueWorker(id)
        return apiDatabase.pokemonDao().get(id).distinctUntilChanged().map {
            it?.toModel()
        }.flowOn(Dispatchers.IO)
    }

    override fun getIds(): Flow<List<Int>> {
        queueWorker()
        return apiDatabase.pokemonDao().getIds().distinctUntilChanged().flowOn(Dispatchers.IO)
    }

    // endregion:   -- Get
    // region:      -- Refresh

    override suspend fun refresh(id: Int) {
        val rawVariety = pokeApi.getPokemonVariety(id)
        val rawSpecies = pokeApi.getPokemonSpecies(rawVariety.species.id)

        val stub = PokemonStub(rawVariety.species.id)
        val detail = PokemonDetailEntity.fromApi(rawVariety)
        val localizedNames = rawSpecies.names.map { PokemonLocalizedNameEntity.fromApi(id, it) }
        val sprites = PokemonSpritesEntity.fromApi(id, rawVariety.sprites)

        val typeStubs = rawVariety.types.map { TypeStub(it.type.id) }
        val junctions = rawVariety.types.map {
            PokemonTypeJunction(
                pokemonId = id,
                typeId = it.type.id
            )
        }

        apiDatabase.withTransaction {
            apiDatabase.pokemonDao().upsertStub(stub)
            apiDatabase.pokemonDao().upsertDetail(detail)
            apiDatabase.pokemonDao().upsertLocalizedName(localizedNames)
            apiDatabase.pokemonDao().upsertSprite(sprites)

            apiDatabase.typeDao().upsertStub(typeStubs)
            apiDatabase.pokemonTypeJunctionDao().upsert(junctions)
        }
    }

    override suspend fun refresh() {
        val count = PokeApi.getPokemonVarietyList(0, 1).count
        val rawPokemonVarietyList = PokeApi.getPokemonVarietyList(0, count).results

        return coroutineScope {
            rawPokemonVarietyList.map {
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
        type = Pokemon::class.simpleName,
        id = id
    )

    // endregion:   -- Worker
}