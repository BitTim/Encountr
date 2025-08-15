/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TeamWithPokemon.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   14.08.25, 22:15
 */

package dev.bittim.encountr.core.data.user.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import dev.bittim.encountr.core.data.user.local.entity.PokemonEntity
import dev.bittim.encountr.core.data.user.local.entity.TeamEntity

data class TeamWithPokemon(
    @Embedded val team: TeamEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "team_id"
    )
    val pokemon: List<PokemonEntity>
)
