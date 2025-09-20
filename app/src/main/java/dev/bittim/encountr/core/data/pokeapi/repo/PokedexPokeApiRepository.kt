/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexPokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   20.09.25, 02:04
 */

package dev.bittim.encountr.core.data.pokeapi.repo

import co.pokeapi.pokekotlin.PokeApi
import co.pokeapi.pokekotlin.model.Pokedex
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class PokedexPokeApiRepository(
    private val pokeApi: PokeApi
) : PokedexRepository {
    override suspend fun get(id: Int): Pokedex? {
        return pokeApi.getPokedex(id)
    }

    override suspend fun getByVersionGroupId(id: Int): List<Pokedex> {
        val versionGroup = pokeApi.getVersionGroup(id)
        return coroutineScope {
            versionGroup.pokedexes.map { handle ->
                async(Dispatchers.IO) { get(handle.id) }
            }.awaitAll().filterNotNull()
        }
    }
}