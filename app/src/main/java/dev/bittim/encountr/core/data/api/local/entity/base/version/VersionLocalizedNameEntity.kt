/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionLocalizedNameEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 23:54
 */

package dev.bittim.encountr.core.data.api.local.entity.base.version

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import co.pokeapi.pokekotlin.model.Name
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString

@Entity(
    tableName = "version_localized_name",
    primaryKeys = ["versionId", "languageId"],
    foreignKeys = [
        ForeignKey(
            entity = VersionStub::class,
            parentColumns = ["id"],
            childColumns = ["versionId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
    ],
    indices = [Index(value = ["languageId"])]
)
data class VersionLocalizedNameEntity(
    val versionId: Int,
    val languageId: Int,
    val value: String,
) {
    fun toModel(): LocalizedString {
        return LocalizedString(
            languageId,
            value = value,
        )
    }

    companion object {
        fun fromApi(versionId: Int, name: Name): VersionLocalizedNameEntity {
            return VersionLocalizedNameEntity(
                versionId = versionId,
                languageId = name.language.id,
                value = name.name,
            )
        }
    }
}
