/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LanguageDetailEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.entity.base.language

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.domain.model.api.language.Language

@Entity(
    tableName = "language_detail",
    foreignKeys = [
        ForeignKey(
            entity = LanguageStub::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
            deferred = true
        )
    ]
)
data class LanguageDetailEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val localizedName: String?,
    val countryCode: String,
) {
    fun toModel(): Language? {
        return Language(
            id = id,
            name = name,
            localizedName = localizedName ?: return null,
            countryCode = countryCode,
        )
    }

    companion object {
        fun fromApi(language: co.pokeapi.pokekotlin.model.Language): LanguageDetailEntity {
            val localizedName = language.names.find { it.language.name == language.name }?.name

            return LanguageDetailEntity(
                id = language.id,
                name = language.name,
                localizedName = localizedName,
                countryCode = language.iso3166,
            )
        }
    }
}
