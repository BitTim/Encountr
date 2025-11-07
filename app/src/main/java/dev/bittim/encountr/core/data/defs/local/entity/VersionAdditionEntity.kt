/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionAdditionEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.defs.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.domain.model.api.pokemon.PokemonSpriteVariant
import dev.bittim.encountr.core.domain.model.api.type.TypeSpriteVariant

@Entity(
    tableName = "version_addition",
    foreignKeys = [
        ForeignKey(
            entity = DefinitionEntity::class,
            parentColumns = ["id"],
            childColumns = ["definition"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class VersionAdditionEntity(
    @PrimaryKey val versionId: Int,
    val definition: Int = 0,
    val imageUrl: String?,
    val pokemonSpriteVariant: PokemonSpriteVariant?,
    val typeSpriteVariant: TypeSpriteVariant?
)
