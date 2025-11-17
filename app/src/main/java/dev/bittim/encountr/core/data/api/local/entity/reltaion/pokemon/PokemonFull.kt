/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonFull.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 19:16
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.pokemon

import androidx.room.Embedded
import androidx.room.Relation
import dev.bittim.encountr.core.data.api.local.entity.base.CombinedEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonSpritesEntity
import dev.bittim.encountr.core.data.api.local.entity.junction.PokedexPokemonJunction
import dev.bittim.encountr.core.data.api.local.entity.junction.PokemonTypeJunction

data class PokemonFull(
    @Embedded val pokemon: PokemonEntity,
    @Relation(
        entity = PokemonLocalizedNameEntity::class,
        parentColumn = "id",
        entityColumn = "pokemonId"
    ) val pokemonLocalizedNames: List<PokemonLocalizedNameEntity>,
    @Relation(
        entity = PokemonSpritesEntity::class,
        parentColumn = "id",
        entityColumn = "pokemonId"
    ) val pokemonSprites: List<PokemonSpritesEntity>,
    @Relation(
        entity = PokedexPokemonJunction::class,
        parentColumn = "id",
        entityColumn = "pokemonId",
        projection = ["pokedexId", "entryNumber"]
    ) val entryNumbers: List<PartialPokedexEntry>,
    @Relation(
        entity = PokemonTypeJunction::class,
        parentColumn = "id",
        entityColumn = "pokemonId",
        projection = ["typeId"]
    ) val typeIds: List<Int>
) : CombinedEntity by pokemon {
    fun toModel() = pokemon.toModel(
        entryNumbers = entryNumbers.associateBy({ it.pokedexId }, { it.entryNumber }),
        localizedNames = pokemonLocalizedNames.map { it.toModel() },
        pokemonSprites = pokemonSprites.map { it.toModel() },
        typeIds = typeIds
    )
}
