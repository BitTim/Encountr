/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GenerationLocalizedNameEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 23:54
 */

package dev.bittim.encountr.core.data.api.local.entity.base.generation

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import co.pokeapi.pokekotlin.model.Name
import dev.bittim.encountr.core.data.api.local.entity.base.language.LanguageStub
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString

@Entity(
    tableName = "generation_localized_name",
    primaryKeys = ["generationId", "languageId"],
    foreignKeys = [
        ForeignKey(
            entity = GenerationStub::class,
            parentColumns = ["id"],
            childColumns = ["generationId"],
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
data class GenerationLocalizedNameEntity(
    val generationId: Int,
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
        fun fromApi(generationId: Int, name: Name): GenerationLocalizedNameEntity {
            return GenerationLocalizedNameEntity(
                generationId = generationId,
                languageId = name.language.id,
                value = name.name,
            )
        }
    }
}
