/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TypeDto.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 23:04
 */

package dev.bittim.encountr.core.data.api.repo.type

import co.pokeapi.pokekotlin.model.Generation
import co.pokeapi.pokekotlin.model.GenerationGameIndex
import co.pokeapi.pokekotlin.model.GenerationIvTypeSprites
import co.pokeapi.pokekotlin.model.GenerationIxTypeSprites
import co.pokeapi.pokekotlin.model.GenerationVTypeSprites
import co.pokeapi.pokekotlin.model.GenerationViTypeSprites
import co.pokeapi.pokekotlin.model.GenerationViiTypeSprites
import co.pokeapi.pokekotlin.model.GenerationViiiTypeSprites
import co.pokeapi.pokekotlin.model.Handle
import co.pokeapi.pokekotlin.model.Move
import co.pokeapi.pokekotlin.model.MoveDamageClass
import co.pokeapi.pokekotlin.model.Name
import co.pokeapi.pokekotlin.model.TypePastDamageRelation
import co.pokeapi.pokekotlin.model.TypePokemon
import co.pokeapi.pokekotlin.model.TypeRelations
import co.pokeapi.pokekotlin.model.TypeSprites
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO: Remove this file when pokekotlin PR is merged
// Most of these functions are copied from the pokekotlin repo, with just the tweak to fix type fetching (My PR)
// https://github.com/PokeAPI/pokekotlin/pull/159
@Serializable
data class TypeDto(
    val id: Int,
    val name: String,
    val damageRelations: TypeRelations,
    val pastDamageRelations: List<TypePastDamageRelation>,
    val gameIndices: List<GenerationGameIndex>,
    val generation: Handle.Named<Generation>,
    val moveDamageClass: Handle.Named<MoveDamageClass>?,
    val names: List<Name>,
    val pokemon: List<TypePokemon>,
    val moves: List<Handle.Named<Move>>,
    val sprites: VersionTypeSprites,
)

@Serializable
data class VersionTypeSprites(
    @SerialName("generation-iii") val generationIii: GenerationIiiTypeSprites,
    @SerialName("generation-iv") val generationIv: GenerationIvTypeSprites,
    @SerialName("generation-v") val generationV: GenerationVTypeSprites,
    @SerialName("generation-vi") val generationVi: GenerationViTypeSprites,
    @SerialName("generation-vii") val generationVii: GenerationViiTypeSprites,
    @SerialName("generation-viii") val generationViii: GenerationViiiTypeSprites,
    @SerialName("generation-ix") val generationIx: GenerationIxTypeSprites,
)

@Serializable
data class GenerationIiiTypeSprites(
    val colosseum: TypeSprites,
    val emerald: TypeSprites,
    @SerialName("firered-leafgreen") val fireredLeafgreen: TypeSprites,
    @SerialName("ruby-sapphire") val rubySapphire: TypeSprites,
    val xd: TypeSprites,
)