/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonRepoImpl.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 10:53
 */

package dev.bittim.encountr.core.data.user.repo

import co.pokeapi.pokekotlin.model.PokemonVariety
import dev.bittim.encountr.core.data.user.local.UserDatabase
import dev.bittim.encountr.core.data.user.local.entity.PokemonEntity
import dev.bittim.encountr.core.domain.model.user.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class PokemonRepoImpl(
    private val userDatabase: UserDatabase
) : PokemonRepo {
    // region:      -- Create

    override suspend fun create(
        save: Uuid,
        pokemon: PokemonVariety,
        caught: Boolean
    ): Pokemon {
        val pokemon = Pokemon(
            id = pokemon.id,
            caught = caught
        )

        userDatabase.pokemonDao.insert(PokemonEntity(pokemon, save))
        return pokemon
    }

    // endregion:   -- Create
    // region:      -- Read

    override fun get(
        save: Uuid,
        pokemon: PokemonVariety
    ): Flow<Pokemon?> {
        return userDatabase.pokemonDao.get(save, pokemon.id).map { it?.toModel() }
    }

    override fun getAll(save: Uuid): Flow<List<Pokemon>> {
        return userDatabase.pokemonDao.getAll(save)
            .map { it.map { pokemonEntity -> pokemonEntity.toModel() } }
    }

    // endregion:   -- Read
    // region:      -- Update

    override suspend fun update(save: Uuid, pokemon: PokemonVariety, caught: Boolean) {
        userDatabase.pokemonDao.update(save, pokemon.id, caught)
    }

    // endregion:   -- Update
    // region:      -- Delete

    override suspend fun delete(save: Uuid, pokemon: PokemonVariety) {
        userDatabase.pokemonDao.delete(save, pokemon.id)
    }

    override suspend fun deleteAll(save: Uuid) {
        userDatabase.pokemonDao.deleteAll(save)
    }

    // endregion:   -- Delete
}