/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonFull.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.pokemon

import androidx.room.Embedded
import androidx.room.Relation
import dev.bittim.encountr.core.data.api.local.entity.base.ExpirableEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonSpritesEntity
import dev.bittim.encountr.core.data.api.local.entity.junction.PokedexPokemonJunction
import dev.bittim.encountr.core.data.api.local.entity.junction.PokemonTypeJunction
import dev.bittim.encountr.core.domain.model.api.Handle

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
) : ExpirableEntity by pokemon {
    fun toModel() = pokemon.toModel(
        entryNumbers = entryNumbers.map { it.toModel() },
        localizedNames = pokemonLocalizedNames.map { it.toModel() },
        pokemonSprites = pokemonSprites.map { it.toModel() },
        types = typeIds.map { Handle(it) }
    )
}
