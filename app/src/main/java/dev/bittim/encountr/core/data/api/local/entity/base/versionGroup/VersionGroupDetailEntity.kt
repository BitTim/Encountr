/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionGroupDetailEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.11.25, 03:05
 */

package dev.bittim.encountr.core.data.api.local.entity.base.versionGroup

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.data.api.local.entity.base.DetailEntity
import dev.bittim.encountr.core.domain.model.api.versionGroup.VersionGroup

@Entity(
    tableName = "version_group_detail",
    foreignKeys = [
        ForeignKey(
            entity = VersionGroupStub::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class VersionGroupDetailEntity(
    @PrimaryKey val id: Int,
    val name: String,
) : DetailEntity {
    fun toModel(
        generationId: Int?,
        versionIds: List<Int>,
        pokedexIds: List<Int>,
    ): VersionGroup {
        return VersionGroup(
            id = id,
            name = name,
            generationId = generationId,
            versionIds = versionIds,
            pokedexIds = pokedexIds
        )
    }

    companion object {
        fun fromApi(versionGroup: co.pokeapi.pokekotlin.model.VersionGroup): VersionGroupDetailEntity {
            return VersionGroupDetailEntity(
                id = versionGroup.id,
                name = versionGroup.name,
            )
        }
    }
}