/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TypeLocalizedNameEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 23:54
 */

package dev.bittim.encountr.core.data.api.local.entity.base.type

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import dev.bittim.encountr.core.data.api.local.entity.base.language.LanguageStub
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString

@Entity(
    tableName = "type_localized_name",
    primaryKeys = ["typeId", "languageId"],
    foreignKeys = [
        ForeignKey(
            entity = TypeStub::class,
            parentColumns = ["id"],
            childColumns = ["typeId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LanguageStub::class,
            parentColumns = ["id"],
            childColumns = ["languageId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["languageId"])]
)
data class TypeLocalizedNameEntity(
    val typeId: Int,
    val languageId: Int,
    val value: String,
) {
    fun toModel(): LocalizedString {
        return LocalizedString(
            languageId = languageId,
            value = value,
        )
    }

    companion object {
        fun fromApi(typeId: Int, name: co.pokeapi.pokekotlin.model.Name): TypeLocalizedNameEntity {
            return TypeLocalizedNameEntity(
                typeId = typeId,
                languageId = name.language.id,
                value = name.name
            )
        }
    }
}
