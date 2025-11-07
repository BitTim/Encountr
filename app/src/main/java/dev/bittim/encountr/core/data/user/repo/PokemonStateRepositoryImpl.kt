/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonStateRepositoryImpl.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.user.repo

import co.pokeapi.pokekotlin.model.PokemonVariety
import dev.bittim.encountr.core.data.user.local.UserDatabase
import dev.bittim.encountr.core.data.user.local.entity.PokemonStateEntity
import dev.bittim.encountr.core.domain.model.user.PokemonState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class PokemonStateRepositoryImpl(
    private val userDatabase: UserDatabase
) : PokemonStateRepository {
    // region:      -- Create

    override suspend fun create(
        save: Uuid,
        pokemon: PokemonVariety,
        caught: Boolean,
        shiny: Boolean
    ): PokemonState {
        val pokemonState = PokemonState(
            id = pokemon.id,
            caught = caught,
            shiny = shiny
        )

        userDatabase.pokemonStateDao.insert(PokemonStateEntity(pokemonState, save))
        return pokemonState
    }

    // endregion:   -- Create
    // region:      -- Read

    override fun get(
        save: Uuid,
        pokemon: PokemonVariety
    ): Flow<PokemonState?> {
        return userDatabase.pokemonStateDao.get(save.toString(), pokemon.id).map { it?.toModel() }
    }

    override fun getAll(save: Uuid): Flow<List<PokemonState>> {
        return userDatabase.pokemonStateDao.getAll(save.toString())
            .map { it.map { pokemonEntity -> pokemonEntity.toModel() } }
    }

    // endregion:   -- Read
    // region:      -- Update

    override suspend fun update(save: Uuid, pokemon: PokemonVariety, caught: Boolean) {
        userDatabase.pokemonStateDao.update(save.toString(), pokemon.id, caught)
    }

    // endregion:   -- Update
    // region:      -- Delete

    override suspend fun delete(save: Uuid, pokemon: PokemonVariety) {
        userDatabase.pokemonStateDao.delete(save.toString(), pokemon.id)
    }

    override suspend fun deleteAll(save: Uuid) {
        userDatabase.pokemonStateDao.deleteAll(save.toString())
    }

    // endregion:   -- Delete
}