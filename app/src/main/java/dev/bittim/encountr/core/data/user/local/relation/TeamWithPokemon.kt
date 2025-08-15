/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TeamWithPokemon.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 14:18
 */

package dev.bittim.encountr.core.data.user.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import dev.bittim.encountr.core.data.user.local.entity.PokemonEntity
import dev.bittim.encountr.core.data.user.local.entity.PokemonTeamCrossRef
import dev.bittim.encountr.core.data.user.local.entity.TeamEntity
import dev.bittim.encountr.core.domain.model.user.Team
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class TeamWithPokemon(
    @Embedded val team: TeamEntity,
    @Relation(
        parentColumn = "id",
        entity = PokemonEntity::class,
        entityColumn = "id",
        associateBy = Junction(
            value = PokemonTeamCrossRef::class,
            parentColumn = "team",
            entityColumn = "pokemon"
        )
    )
    val pokemon: List<PokemonEntity>
) {
    @OptIn(ExperimentalUuidApi::class)
    fun toModel(): Team {
        return Team(
            id = Uuid.parse(team.id),
            name = team.name,
            pokemon = pokemon.map { it.toModel() }
        )
    }
}
