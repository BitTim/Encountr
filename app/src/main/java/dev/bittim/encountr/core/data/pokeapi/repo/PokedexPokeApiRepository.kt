/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexPokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.09.25, 00:53
 */

package dev.bittim.encountr.core.data.pokeapi.repo

import co.pokeapi.pokekotlin.PokeApi
import co.pokeapi.pokekotlin.model.Pokedex
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class PokedexPokeApiRepository : PokedexRepository {
    override suspend fun get(id: Int): Pokedex? {
        return PokeApi.getPokedex(id)
    }

    override suspend fun getByVersionGroupId(id: Int): List<Pokedex> {
        val versionGroup = PokeApi.getVersionGroup(id)
        return coroutineScope {
            versionGroup.pokedexes.map { handle ->
                async(Dispatchers.IO) { get(handle.id) }
            }.awaitAll().filterNotNull()
        }
    }
}