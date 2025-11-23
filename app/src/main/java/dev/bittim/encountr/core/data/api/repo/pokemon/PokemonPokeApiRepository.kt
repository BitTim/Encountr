/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonPokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   23.11.25, 18:54
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
import dev.bittim.encountr.core.domain.model.api.pokemon.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class PokemonPokeApiRepository(
    workManager: WorkManager,
    private val apiDatabase: ApiDatabase,
    private val pokeApi: PokeApi,
) : PokemonRepository(workManager, apiDatabase) {
    // region:      -- Get

    override fun get(id: Int): Flow<Pokemon?> {
        return apiDatabase.pokemonDao().get(id)
            .onStart { refresh(id) }
            .distinctUntilChanged()
            .map { it?.toModel() }
            .flowOn(Dispatchers.IO)
    }

    override fun getIds(): Flow<List<Int>> {
        return apiDatabase.pokemonDao().getIds()
            .onStart { refresh() }
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
    }

    // endregion:   -- Get
    // region:      -- Fetch

    override suspend fun fetch(id: Int) {
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

    override suspend fun fetch() {
        val count = PokeApi.getPokemonVarietyList(0, 1).count
        val rawPokemonVarietyList = PokeApi.getPokemonVarietyList(0, count).results

        return coroutineScope {
            rawPokemonVarietyList.map {
                async(Dispatchers.IO) {
                    fetch(it.id)
                }
            }.awaitAll()
        }
    }

    // endregion:   -- Fetch
}