/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GenerationDetailEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.11.25, 03:00
 */

package dev.bittim.encountr.core.data.api.local.entity.base.generation

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.data.api.local.entity.base.DetailEntity
import dev.bittim.encountr.core.domain.model.api.generation.Generation
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString

@Entity(
    tableName = "generation_detail",
    foreignKeys = [
        ForeignKey(
            entity = GenerationStub::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class GenerationDetailEntity(
    @PrimaryKey val id: Int,
    val name: String,
) : DetailEntity {
    fun toModel(
        localizedNames: List<LocalizedString>,
        versionGroups: List<Int>,
    ): Generation {
        return Generation(
            id = id,
            name = name,
            localizedNames = localizedNames,
            versionGroupIds = versionGroups
        )
    }

    companion object {
        fun fromApi(generation: co.pokeapi.pokekotlin.model.Generation): GenerationDetailEntity {
            return GenerationDetailEntity(
                id = generation.id,
                name = generation.name
            )
        }
    }
}
