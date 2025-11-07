/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GenerationEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.entity.base.generation

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.data.api.local.entity.base.ExpirableEntity
import dev.bittim.encountr.core.domain.model.api.Handle
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString
import dev.bittim.encountr.core.domain.model.api.version.Generation
import dev.bittim.encountr.core.domain.model.api.version.VersionGroup

@Entity(
    tableName = "generation"
)
data class GenerationEntity(
    @PrimaryKey val id: Int,
    override val expiresAt: Long?,
    val name: String?,
) : ExpirableEntity {
    fun toModel(
        localizedNames: List<LocalizedString>,
        versionGroups: List<Handle<VersionGroup>>,
    ): Generation? {
        return Generation(
            id = id,
            name = name ?: return null,
            localizedNames = localizedNames,
            versionGroups = versionGroups
        )
    }

    companion object {
        fun fromApi(generation: co.pokeapi.pokekotlin.model.Generation): GenerationEntity {
            return GenerationEntity(
                id = generation.id,
                name = generation.name,
                expiresAt = ExpirableEntity.calcExpiryTime()
            )
        }

        fun empty(id: Int): GenerationEntity {
            return GenerationEntity(
                id = id,
                name = null,
                expiresAt = null,
            )
        }
    }
}
