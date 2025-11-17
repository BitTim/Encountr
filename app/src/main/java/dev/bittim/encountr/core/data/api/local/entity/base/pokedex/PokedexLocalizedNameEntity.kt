/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexLocalizedNameEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 23:54
 */

package dev.bittim.encountr.core.data.api.local.entity.base.pokedex

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import co.pokeapi.pokekotlin.model.Name
import dev.bittim.encountr.core.data.api.local.entity.base.language.LanguageStub
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString

@Entity(
    tableName = "pokedex_localized_name",
    primaryKeys = ["pokedexId", "languageId"],
    foreignKeys = [
        ForeignKey(
            entity = PokedexStub::class,
            parentColumns = ["id"],
            childColumns = ["pokedexId"],
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
data class PokedexLocalizedNameEntity(
    val pokedexId: Int,
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
        fun fromApi(pokedexId: Int, name: Name): PokedexLocalizedNameEntity {
            return PokedexLocalizedNameEntity(
                pokedexId = pokedexId,
                languageId = name.language.id,
                value = name.name
            )
        }
    }
}
