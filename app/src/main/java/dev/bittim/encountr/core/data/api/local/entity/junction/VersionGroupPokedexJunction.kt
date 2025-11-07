/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionGroupPokedexJunction.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.entity.junction

import androidx.room.Entity
import androidx.room.ForeignKey
import dev.bittim.encountr.core.data.api.local.entity.base.ExpirableEntity
import dev.bittim.encountr.core.data.api.local.entity.base.versionGroup.VersionGroupEntity

@Entity(
    tableName = "version_group_pokedex_junction",
    primaryKeys = ["pokedexId", "versionGroupId"],
    foreignKeys = [
        ForeignKey(
            entity = VersionGroupEntity::class,
            parentColumns = ["id"],
            childColumns = ["versionGroupId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class VersionGroupPokedexJunction(
    val versionGroupId: Int,
    val pokedexId: Int,
    override val expiresAt: Long,
) : ExpirableEntity
