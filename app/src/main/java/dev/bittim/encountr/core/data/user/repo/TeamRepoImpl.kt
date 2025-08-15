/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TeamRepoImpl.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 11:03
 */

package dev.bittim.encountr.core.data.user.repo

import co.pokeapi.pokekotlin.model.PokemonVariety
import dev.bittim.encountr.core.data.user.local.UserDatabase
import dev.bittim.encountr.core.data.user.local.entity.PokemonTeamCrossRef
import dev.bittim.encountr.core.data.user.local.entity.TeamEntity
import dev.bittim.encountr.core.domain.model.user.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class TeamRepoImpl(
    private val userDatabase: UserDatabase
) : TeamRepo {
    // region:      -- Create

    override suspend fun create(save: Uuid, name: String): Team {
        val team = Team(
            id = Uuid.random(),
            name = name,
            pokemon = emptyList()
        )

        userDatabase.teamDao.insert(TeamEntity(team, save))
        return team
    }

    // endregion:   -- Create
    // region:      -- Read

    override fun get(
        save: Uuid,
        id: Uuid
    ): Flow<Team?> {
        return userDatabase.teamDao.get(save, id).map { it?.toModel() }
    }

    override fun getAll(save: Uuid): Flow<List<Team>> {
        return userDatabase.teamDao.getAll(save)
            .map { it.map { teamEntity -> teamEntity.toModel() } }
    }

    // endregion:   -- Read
    // region:      -- Update

    override suspend fun update(save: Uuid, id: Uuid, name: String) {
        userDatabase.teamDao.update(save, id, name)
    }

    override suspend fun addPokemon(
        save: Uuid,
        id: Uuid,
        pokemon: PokemonVariety
    ) {
        userDatabase.pokemonTeamRefDao.insert(PokemonTeamCrossRef(save, id, pokemon.id))
    }

    override suspend fun removePokemon(
        save: Uuid,
        id: Uuid,
        pokemon: PokemonVariety
    ) {
        userDatabase.pokemonTeamRefDao.delete(save, id, pokemon.id)
    }

    // endregion:   -- Update
    // region:      -- Delete

    override suspend fun delete(save: Uuid, id: Uuid) {
        userDatabase.teamDao.delete(save, id)
    }

    override suspend fun deleteAll(save: Uuid) {
        userDatabase.teamDao.deleteAll(save)
    }

    // endregion:   -- Delete
}