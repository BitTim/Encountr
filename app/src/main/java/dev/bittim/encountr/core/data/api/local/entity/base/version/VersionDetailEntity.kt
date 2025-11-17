/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionDetailEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 20:19
 */

package dev.bittim.encountr.core.data.api.local.entity.base.version

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.data.api.local.entity.base.DetailEntity
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString
import dev.bittim.encountr.core.domain.model.api.pokemon.PokemonSpriteVariant
import dev.bittim.encountr.core.domain.model.api.type.TypeSpriteVariant
import dev.bittim.encountr.core.domain.model.api.version.Version
import dev.bittim.encountr.core.domain.model.defs.VersionAddition

@Entity(
    tableName = "version_detail",
    foreignKeys = [
        ForeignKey(
            entity = VersionStub::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class VersionDetailEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String?,
    val pokemonSpriteVariant: PokemonSpriteVariant,
    val typeSpriteVariant: TypeSpriteVariant?,
) : DetailEntity {
    fun toModel(
        versionGroupId: Int?,
        localizedNames: List<LocalizedString>,
    ): Version {
        return Version(
            id = id,
            name = name,
            localizedNames = localizedNames,
            versionGroupId = versionGroupId,
            imageUrl = imageUrl,
            pokemonSpriteVariant = pokemonSpriteVariant,
            typeSpriteVariant = typeSpriteVariant,
        )
    }

    companion object {
        fun fromApi(
            version: co.pokeapi.pokekotlin.model.Version,
            versionAddition: VersionAddition,
        ): VersionDetailEntity {
            return VersionDetailEntity(
                id = version.id,
                name = version.name,
                imageUrl = versionAddition.imageUrl,
                pokemonSpriteVariant = versionAddition.pokemonSpriteVariant,
                typeSpriteVariant = versionAddition.typeSpriteVariant,
            )
        }
    }
}
