/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TypeLocalizedNameEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.entity.base.type

import androidx.room.Entity
import androidx.room.ForeignKey
import dev.bittim.encountr.core.data.api.local.entity.base.ExpirableEntity
import dev.bittim.encountr.core.data.api.local.entity.base.language.LanguageEntity
import dev.bittim.encountr.core.domain.model.api.Handle
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString

@Entity(
    tableName = "type_localized_name",
    primaryKeys = ["typeId", "languageId"],
    foreignKeys = [
        ForeignKey(
            entity = TypeEntity::class,
            parentColumns = ["id"],
            childColumns = ["typeId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LanguageEntity::class,
            parentColumns = ["id"],
            childColumns = ["languageId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class TypeLocalizedNameEntity(
    val typeId: Int,
    val languageId: Int,
    override val expiresAt: Long,
    val value: String,
) : ExpirableEntity {
    fun toModel(): LocalizedString {
        return LocalizedString(
            Handle(languageId),
            value = value,
        )
    }

    companion object {
        fun fromApi(typeId: Int, name: co.pokeapi.pokekotlin.model.Name): TypeLocalizedNameEntity {
            return TypeLocalizedNameEntity(
                typeId = typeId,
                languageId = name.language.id,
                value = name.name,
                expiresAt = ExpirableEntity.calcExpiryTime()
            )
        }
    }
}
