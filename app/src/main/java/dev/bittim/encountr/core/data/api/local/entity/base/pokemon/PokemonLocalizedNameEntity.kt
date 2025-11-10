/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonLocalizedNameEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.entity.base.pokemon

import androidx.room.Entity
import androidx.room.ForeignKey
import co.pokeapi.pokekotlin.model.Name
import dev.bittim.encountr.core.data.api.local.entity.base.language.LanguageStub
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString

@Entity(
    tableName = "pokemon_localized_name",
    primaryKeys = ["pokemonId", "languageId"],
    foreignKeys = [
        ForeignKey(
            entity = PokemonStub::class,
            parentColumns = ["id"],
            childColumns = ["pokemonId"],
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
    ]
)
data class PokemonLocalizedNameEntity(
    val pokemonId: Int,
    val languageId: Int,
    val value: String
) {
    fun toModel(): LocalizedString {
        return LocalizedString(
            languageId = languageId,
            value = value,
        )
    }

    companion object {
        fun fromApi(
            pokemonId: Int,
            name: Name
        ): PokemonLocalizedNameEntity {
            return PokemonLocalizedNameEntity(
                pokemonId = pokemonId,
                languageId = name.language.id,
                value = name.name
            )
        }
    }
}
