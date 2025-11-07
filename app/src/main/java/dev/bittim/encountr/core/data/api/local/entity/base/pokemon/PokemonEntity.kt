/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.entity.base.pokemon

import androidx.room.Entity
import androidx.room.PrimaryKey
import co.pokeapi.pokekotlin.model.PokemonVariety
import dev.bittim.encountr.core.data.api.local.entity.base.ExpirableEntity
import dev.bittim.encountr.core.domain.model.api.Handle
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString
import dev.bittim.encountr.core.domain.model.api.pokemon.PokedexEntry
import dev.bittim.encountr.core.domain.model.api.pokemon.Pokemon
import dev.bittim.encountr.core.domain.model.api.pokemon.PokemonSprites
import dev.bittim.encountr.core.domain.model.api.type.Type

@Entity(
    tableName = "pokemon"
)
data class PokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    override val expiresAt: Long,
    val height: String,
    val weight: String,
) : ExpirableEntity {
    fun toModel(
        entryNumbers: List<PokedexEntry>,
        localizedNames: List<LocalizedString>,
        pokemonSprites: List<PokemonSprites>,
        types: List<Handle<Type>>
    ): Pokemon {
        return Pokemon(
            id = id,
            entryNumbers = entryNumbers,
            name = name,
            localizedNames = localizedNames,
            height = height,
            weight = weight,
            sprites = pokemonSprites,
            types = types,
        )
    }

    companion object {
        private fun formatHeight(height: Int): String {
            return "${height / 10} m"
        }

        private fun formatWeight(weight: Int): String {
            return "${weight / 10} kg"
        }

        fun fromApi(pokemonVariety: PokemonVariety): PokemonEntity {
            return PokemonEntity(
                id = pokemonVariety.id,
                name = pokemonVariety.name,
                height = formatHeight(pokemonVariety.height),
                weight = formatWeight(pokemonVariety.weight),
                expiresAt = ExpirableEntity.calcExpiryTime(),
            )
        }
    }
}
