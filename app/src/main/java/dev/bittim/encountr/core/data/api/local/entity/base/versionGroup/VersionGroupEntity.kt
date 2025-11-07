/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionGroupEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.entity.base.versionGroup

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.data.api.local.entity.base.ExpirableEntity
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationEntity
import dev.bittim.encountr.core.domain.model.api.Handle
import dev.bittim.encountr.core.domain.model.api.pokedex.Pokedex
import dev.bittim.encountr.core.domain.model.api.version.Version
import dev.bittim.encountr.core.domain.model.api.version.VersionGroup

@Entity(
    tableName = "version_group",
    foreignKeys = [
        ForeignKey(
            entity = GenerationEntity::class,
            parentColumns = ["id"],
            childColumns = ["generationId"],
            onDelete = ForeignKey.Companion.CASCADE,
            onUpdate = ForeignKey.Companion.CASCADE
        )
    ]
)
data class VersionGroupEntity(
    @PrimaryKey val id: Int,
    override val expiresAt: Long?,
    val name: String?,
    val generationId: Int,
) : ExpirableEntity {
    fun toModel(
        versions: List<Handle<Version>>,
        pokedexes: List<Handle<Pokedex>>,
    ): VersionGroup? {
        return VersionGroup(
            id = id,
            name = name ?: return null,
            generation = Handle(generationId),
            versions = versions,
            pokedexes = pokedexes
        )
    }

    companion object {
        fun fromApi(versionGroup: co.pokeapi.pokekotlin.model.VersionGroup): VersionGroupEntity {
            return VersionGroupEntity(
                id = versionGroup.id,
                name = versionGroup.name,
                generationId = versionGroup.generation.id,
                expiresAt = ExpirableEntity.calcExpiryTime(),
            )
        }

        fun empty(id: Int, generationId: Int): VersionGroupEntity {
            return VersionGroupEntity(
                id = id,
                name = null,
                generationId = generationId,
                expiresAt = null,
            )
        }
    }
}