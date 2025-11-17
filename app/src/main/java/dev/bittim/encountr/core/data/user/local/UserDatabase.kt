/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       UserDatabase.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 23:57
 */

package dev.bittim.encountr.core.data.user.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.bittim.encountr.core.data.user.local.dao.PokemonStateDao
import dev.bittim.encountr.core.data.user.local.dao.PokemonTeamRefDao
import dev.bittim.encountr.core.data.user.local.dao.SaveDao
import dev.bittim.encountr.core.data.user.local.dao.TeamDao
import dev.bittim.encountr.core.data.user.local.entity.PokemonStateEntity
import dev.bittim.encountr.core.data.user.local.entity.PokemonTeamJunction
import dev.bittim.encountr.core.data.user.local.entity.SaveEntity
import dev.bittim.encountr.core.data.user.local.entity.TeamEntity

@Database(
    version = 1,
    entities = [
        SaveEntity::class,
        TeamEntity::class,
        PokemonStateEntity::class,
        PokemonTeamJunction::class,
    ],
    exportSchema = false
)
abstract class UserDatabase : RoomDatabase() {
    abstract val saveDao: SaveDao
    abstract val teamDao: TeamDao
    abstract val pokemonStateDao: PokemonStateDao
    abstract val pokemonTeamRefDao: PokemonTeamRefDao
}