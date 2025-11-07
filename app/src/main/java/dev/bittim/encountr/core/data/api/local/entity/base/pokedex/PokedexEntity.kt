/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.entity.base.pokedex

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.data.api.local.entity.base.ExpirableEntity
import dev.bittim.encountr.core.domain.model.api.Handle
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString
import dev.bittim.encountr.core.domain.model.api.pokedex.Pokedex
import dev.bittim.encountr.core.domain.model.api.pokemon.Pokemon

@Entity(
    tableName = "pokedex",
)
data class PokedexEntity(
    @PrimaryKey val id: Int,
    val name: String,
    override val expiresAt: Long,
    val isMainSeries: Boolean,
) : ExpirableEntity {
    fun toModel(
        localizedNames: List<LocalizedString>,
        pokemon: List<Handle<Pokemon>>,
    ): Pokedex {
        return Pokedex(
            id = id,
            name = name,
            isMainSeries = isMainSeries,
            localizedNames = localizedNames,
            pokemon = pokemon,
        )
    }

    companion object {
        fun fromApi(pokedex: co.pokeapi.pokekotlin.model.Pokedex): PokedexEntity {
            return PokedexEntity(
                id = pokedex.id,
                name = pokedex.name,
                isMainSeries = pokedex.isMainSeries,
                expiresAt = ExpirableEntity.calcExpiryTime(),
            )
        }
    }
}
