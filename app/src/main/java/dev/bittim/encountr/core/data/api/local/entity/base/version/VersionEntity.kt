/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.entity.base.version

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.data.api.local.entity.base.ExpirableEntity
import dev.bittim.encountr.core.data.api.local.entity.base.versionGroup.VersionGroupEntity
import dev.bittim.encountr.core.domain.model.api.Handle
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString
import dev.bittim.encountr.core.domain.model.api.version.Version

@Entity(
    tableName = "version",
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
data class VersionEntity(
    @PrimaryKey val id: Int,
    override val expiresAt: Long?,
    val name: String?,
    val versionGroupId: Int,
    val imageUrl: String?,
) : ExpirableEntity {
    fun toModel(
        localizedNames: List<LocalizedString>,
    ): Version? {
        return Version(
            id = id,
            name = name ?: return null,
            localizedNames = localizedNames,
            versionGroup = Handle(versionGroupId),
            imageUrl = imageUrl
        )
    }

    companion object {
        fun fromApi(
            version: co.pokeapi.pokekotlin.model.Version,
            imageUrl: String?
        ): VersionEntity {
            return VersionEntity(
                id = version.id,
                name = version.name,
                versionGroupId = version.versionGroup.id,
                imageUrl = imageUrl,
                expiresAt = ExpirableEntity.calcExpiryTime(),
            )
        }

        fun empty(id: Int, versionGroupId: Int): VersionEntity {
            return VersionEntity(
                id = id,
                name = null,
                versionGroupId = versionGroupId,
                imageUrl = null,
                expiresAt = null,
            )
        }
    }
}
