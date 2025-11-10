/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexDetailEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.entity.base.pokedex

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString
import dev.bittim.encountr.core.domain.model.api.pokedex.Pokedex

@Entity(
    tableName = "pokedex_detail",
    foreignKeys = [
        ForeignKey(
            entity = PokedexStub::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class PokedexDetailEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val isMainSeries: Boolean,
) {
    fun toModel(
        localizedNames: List<LocalizedString>,
        pokemonIds: List<Int>,
    ): Pokedex {
        return Pokedex(
            id = id,
            name = name,
            isMainSeries = isMainSeries,
            localizedNames = localizedNames,
            pokemonIds = pokemonIds,
        )
    }

    companion object {
        fun fromApi(pokedex: co.pokeapi.pokekotlin.model.Pokedex): PokedexDetailEntity {
            return PokedexDetailEntity(
                id = pokedex.id,
                name = pokedex.name,
                isMainSeries = pokedex.isMainSeries
            )
        }
    }
}
