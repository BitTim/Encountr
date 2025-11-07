/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonPokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.repo.pokemon

import androidx.work.WorkManager
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.local.entity.base.ExpirableEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonSpritesEntity
import dev.bittim.encountr.core.data.api.local.entity.junction.PokemonTypeJunction
import dev.bittim.encountr.core.data.api.worker.ApiSyncWorker
import dev.bittim.encountr.core.domain.model.api.Handle
import dev.bittim.encountr.core.domain.model.api.pokemon.PokedexEntry
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

    override fun get(): Flow<List<Pokemon>> {
        queueWorker()
        return apiDatabase.pokemonDao().get().distinctUntilChanged().map { list ->
            list.map { it.toModel() }
        }.flowOn(Dispatchers.IO)
    }

    // endregion:   -- Get
    // region:      -- Refresh

    override suspend fun refresh(id: Int): Pokemon {
        val rawPokemonVariety = pokeApi.getPokemonVariety(id)
        val rawPokemonSpecies = pokeApi.getPokemonSpecies(rawPokemonVariety.species.id)

        val pokemonEntity = PokemonEntity.fromApi(rawPokemonVariety)
        val pokemonOverviewLocalizedNameEntities =
            rawPokemonSpecies.names.map { PokemonLocalizedNameEntity.fromApi(id, it) }
        val pokemonSpritesEntities = PokemonSpritesEntity.fromApi(id, rawPokemonVariety.sprites)

        val junctions = rawPokemonVariety.types.map {
            PokemonTypeJunction(
                pokemonId = id,
                typeId = it.type.id,
                expiresAt = ExpirableEntity.calcExpiryTime()
            )
        }

        apiDatabase.pokemonDao().upsert(
            pokemonEntity,
            pokemonOverviewLocalizedNameEntities,
            pokemonSpritesEntities,
            junctions
        )
        return pokemonEntity.toModel(
            entryNumbers = rawPokemonSpecies.pokedexNumbers.map {
                PokedexEntry(
                    pokedex = Handle(it.pokedex.id),
                    entryNumber = it.entryNumber
                )
            },
            localizedNames = pokemonOverviewLocalizedNameEntities.map { it.toModel() },
            pokemonSprites = pokemonSpritesEntities.map { it.toModel() },
            types = rawPokemonVariety.types.map { Handle(it.type.id) }
        )
    }

    override suspend fun refresh(): List<Pokemon> {
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