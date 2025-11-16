/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonDetailEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.11.25, 03:04
 */

package dev.bittim.encountr.core.data.api.local.entity.base.pokemon

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import co.pokeapi.pokekotlin.model.PokemonVariety
import dev.bittim.encountr.core.data.api.local.entity.base.DetailEntity
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString
import dev.bittim.encountr.core.domain.model.api.pokemon.PokedexEntry
import dev.bittim.encountr.core.domain.model.api.pokemon.Pokemon
import dev.bittim.encountr.core.domain.model.api.pokemon.PokemonSprites

@Entity(
    tableName = "pokemon_detail",
    foreignKeys = [
        ForeignKey(
            entity = PokemonStub::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class PokemonDetailEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
) : DetailEntity {
    private fun formatHeight(height: Int): String {
        return "${height / 10} m"
    }

    private fun formatWeight(weight: Int): String {
        return "${weight / 10} kg"
    }

    fun toModel(
        entryNumbers: List<PokedexEntry>,
        localizedNames: List<LocalizedString>,
        pokemonSprites: List<PokemonSprites>,
        typeIds: List<Int>
    ): Pokemon {
        return Pokemon(
            id = id,
            entryNumbers = entryNumbers,
            name = name,
            localizedNames = localizedNames,
            height = formatHeight(height),
            weight = formatWeight(weight),
            sprites = pokemonSprites,
            typeIds = typeIds,
        )
    }

    companion object {
        fun fromApi(pokemonVariety: PokemonVariety): PokemonDetailEntity {
            return PokemonDetailEntity(
                id = pokemonVariety.id,
                name = pokemonVariety.name,
                height = pokemonVariety.height,
                weight = pokemonVariety.weight
            )
        }
    }
}
