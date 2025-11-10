/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.pokemon

import androidx.room.Embedded
import androidx.room.Relation
import dev.bittim.encountr.core.data.api.local.entity.base.TimestampedEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonStub
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString
import dev.bittim.encountr.core.domain.model.api.pokemon.PokedexEntry
import dev.bittim.encountr.core.domain.model.api.pokemon.PokemonSprites

data class PokemonEntity(
    @Embedded val stub: PokemonStub,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    ) val detail: PokemonDetailEntity
) : TimestampedEntity by stub {
    fun toModel(
        entryNumbers: List<PokedexEntry>,
        localizedNames: List<LocalizedString>,
        pokemonSprites: List<PokemonSprites>,
        typeIds: List<Int>
    ) = detail.toModel(
        entryNumbers = entryNumbers,
        localizedNames = localizedNames,
        pokemonSprites = pokemonSprites,
        typeIds = typeIds
    )
}
