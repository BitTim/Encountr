/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TeamWithPokemon.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.user.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import dev.bittim.encountr.core.data.user.local.entity.PokemonStateEntity
import dev.bittim.encountr.core.data.user.local.entity.PokemonTeamJunction
import dev.bittim.encountr.core.data.user.local.entity.TeamEntity
import dev.bittim.encountr.core.domain.model.user.Team
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class TeamWithPokemon(
    @Embedded val team: TeamEntity,
    @Relation(
        entity = PokemonStateEntity::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = PokemonTeamJunction::class,
            parentColumn = "team",
            entityColumn = "pokemonId"
        )
    )
    val pokemon: List<PokemonStateEntity>
) {
    @OptIn(ExperimentalUuidApi::class)
    fun toModel(): Team {
        return Team(
            id = Uuid.parse(team.id),
            name = team.name,
            pokemonStates = pokemon.map { it.toModel() }
        )
    }
}
