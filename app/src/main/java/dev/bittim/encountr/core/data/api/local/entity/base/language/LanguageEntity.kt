/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LanguageEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.entity.base.language

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.data.api.local.entity.base.ExpirableEntity
import dev.bittim.encountr.core.domain.model.api.language.Language

@Entity(tableName = "language")
data class LanguageEntity(
    @PrimaryKey val id: Int,
    override val expiresAt: Long?,
    val name: String?,
    val localizedName: String?,
    val countryCode: String?,
) : ExpirableEntity {
    fun toModel(): Language? {
        return Language(
            id = id,
            name = name ?: return null,
            localizedName = localizedName ?: return null,
            countryCode = countryCode ?: return null,
        )
    }

    companion object {
        fun fromApi(language: co.pokeapi.pokekotlin.model.Language): LanguageEntity {
            val localizedName = language.names.find { it.language.name == language.name }?.name

            return LanguageEntity(
                id = language.id,
                name = language.name,
                localizedName = localizedName,
                countryCode = language.iso3166,
                expiresAt = ExpirableEntity.calcExpiryTime(),
            )
        }

        fun empty(id: Int): LanguageEntity {
            return LanguageEntity(
                id = id,
                name = null,
                localizedName = null,
                countryCode = null,
                expiresAt = null,
            )
        }
    }
}
