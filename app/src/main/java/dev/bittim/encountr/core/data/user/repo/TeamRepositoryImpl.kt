/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TeamRepositoryImpl.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.user.repo

import dev.bittim.encountr.core.data.user.local.UserDatabase
import dev.bittim.encountr.core.data.user.local.entity.PokemonTeamJunction
import dev.bittim.encountr.core.data.user.local.entity.TeamEntity
import dev.bittim.encountr.core.domain.model.user.Team
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class TeamRepositoryImpl(
    private val userDatabase: UserDatabase
) : TeamRepository {
    // region:      -- Create

    override suspend fun create(save: Uuid, name: String): Team {
        val team = Team(
            id = Uuid.random(),
            name = name,
            pokemonStates = emptyList()
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
        return userDatabase.teamDao.get(save.toString(), id.toString()).map { it?.toModel() }
    }

    override fun getAll(save: Uuid): Flow<List<Team>> {
        return userDatabase.teamDao.getAll(save.toString())
            .map { it.map { teamEntity -> teamEntity.toModel() } }
    }

    // endregion:   -- Read
    // region:      -- Update

    override suspend fun update(save: Uuid, id: Uuid, name: String) {
        userDatabase.teamDao.update(save.toString(), id.toString(), name)
    }

    override suspend fun addPokemon(
        id: Uuid,
        pokemonId: Int
    ) {
        userDatabase.pokemonTeamRefDao.insert(
            PokemonTeamJunction(
                id.toString(),
                pokemonId
            )
        )
    }

    override suspend fun removePokemon(
        id: Uuid,
        pokemonId: Int
    ) {
        userDatabase.pokemonTeamRefDao.delete(id.toString(), pokemonId)
    }

    // endregion:   -- Update
    // region:      -- Delete

    override suspend fun delete(save: Uuid, id: Uuid) {
        userDatabase.teamDao.delete(save.toString(), id.toString())
    }

    override suspend fun deleteAll(save: Uuid) {
        userDatabase.teamDao.deleteAll(save.toString())
    }

    // endregion:   -- Delete
}