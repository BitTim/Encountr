/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       UserDatabase.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 09:18
 */

package dev.bittim.encountr.core.data.user.local

import androidx.room.Database
import dev.bittim.encountr.core.data.user.local.dao.PokemonDao
import dev.bittim.encountr.core.data.user.local.dao.PokemonTeamRefDao
import dev.bittim.encountr.core.data.user.local.dao.SaveDao
import dev.bittim.encountr.core.data.user.local.dao.TeamDao
import dev.bittim.encountr.core.data.user.local.entity.PokemonEntity
import dev.bittim.encountr.core.data.user.local.entity.PokemonTeamCrossRef
import dev.bittim.encountr.core.data.user.local.entity.SaveEntity
import dev.bittim.encountr.core.data.user.local.entity.TeamEntity

@Database(
    version = 1,
    entities = [
        SaveEntity::class,
        TeamEntity::class,
        PokemonEntity::class,
        PokemonTeamCrossRef::class,
    ]
)
abstract class UserDatabase {
    abstract val saveDao: SaveDao
    abstract val teamDao: TeamDao
    abstract val pokemonDao: PokemonDao
    abstract val pokemonTeamRefDao: PokemonTeamRefDao
}