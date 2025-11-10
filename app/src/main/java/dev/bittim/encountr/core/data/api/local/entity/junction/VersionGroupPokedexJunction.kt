/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionGroupPokedexJunction.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.entity.junction

import androidx.room.Entity
import androidx.room.ForeignKey
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexStub
import dev.bittim.encountr.core.data.api.local.entity.base.versionGroup.VersionGroupStub

@Entity(
    tableName = "version_group_pokedex_junction",
    primaryKeys = ["pokedexId", "versionGroupId"],
    foreignKeys = [
        ForeignKey(
            entity = VersionGroupStub::class,
            parentColumns = ["id"],
            childColumns = ["versionGroupId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PokedexStub::class,
            parentColumns = ["id"],
            childColumns = ["pokedexId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class VersionGroupPokedexJunction(
    val versionGroupId: Int,
    val pokedexId: Int
)
