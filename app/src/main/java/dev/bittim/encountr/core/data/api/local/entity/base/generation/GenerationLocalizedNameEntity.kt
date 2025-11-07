/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GenerationLocalizedNameEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.entity.base.generation

import androidx.room.Entity
import androidx.room.ForeignKey
import co.pokeapi.pokekotlin.model.Name
import dev.bittim.encountr.core.data.api.local.entity.base.ExpirableEntity
import dev.bittim.encountr.core.data.api.local.entity.base.language.LanguageEntity
import dev.bittim.encountr.core.domain.model.api.Handle
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString

@Entity(
    tableName = "generation_localized_name",
    primaryKeys = ["generationId", "languageId"],
    foreignKeys = [
        ForeignKey(
            entity = GenerationEntity::class,
            parentColumns = ["id"],
            childColumns = ["generationId"],
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
data class GenerationLocalizedNameEntity(
    val generationId: Int,
    val languageId: Int,
    override val expiresAt: Long,
    val value: String,
) : ExpirableEntity {
    fun toModel(): LocalizedString {
        return LocalizedString(
            language = Handle(languageId),
            value = value,
        )
    }

    companion object {
        fun fromApi(generationId: Int, name: Name): GenerationLocalizedNameEntity {
            return GenerationLocalizedNameEntity(
                generationId = generationId,
                languageId = name.language.id,
                value = name.name,
                expiresAt = ExpirableEntity.calcExpiryTime()
            )
        }
    }
}
