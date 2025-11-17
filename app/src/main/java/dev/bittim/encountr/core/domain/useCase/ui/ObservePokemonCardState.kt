/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ObservePokemonCardState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 20:31
 */

package dev.bittim.encountr.core.domain.useCase.ui

import dev.bittim.encountr.content.ui.components.PokemonCardState
import dev.bittim.encountr.core.data.api.repo.pokemon.PokemonRepository
import dev.bittim.encountr.core.domain.model.api.pokemon.PokemonSpriteVariant
import dev.bittim.encountr.core.domain.model.api.type.TypeSpriteVariant
import dev.bittim.encountr.core.domain.useCase.util.ObserveLocalizedName
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ObservePokemonCardState(
    private val observeLocalizedName: ObserveLocalizedName,
    private val observePokemonCardTypeState: ObservePokemonCardTypeState,
    private val pokemonRepository: PokemonRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(
        pokemonId: Int,
        pokedexId: Int,
        pokemonSpriteVariant: PokemonSpriteVariant,
        typeSpriteVariant: TypeSpriteVariant?
    ): Flow<PokemonCardState?> {
        return pokemonRepository.get(pokemonId).flatMapLatest { pokemon ->
            val localizedNameFlow =
                if (pokemon != null) observeLocalizedName(pokemon.localizedNames, pokemon.name)
                else flowOf(null)

            localizedNameFlow.flatMapLatest { localizedName ->
                val typesFlow =
                    if (pokemon != null) combine(pokemon.typeIds.map { typeId ->
                        observePokemonCardTypeState(typeId, typeSpriteVariant)
                    }) { it.toList() }
                    else flowOf(null)

                typesFlow.map { typeStates ->
                    val sprites = pokemon?.sprites?.find { it.variant == pokemonSpriteVariant }
                    if (pokemon == null || localizedName == null || sprites == null || typeStates == null) return@map null

                    PokemonCardState(
                        id = pokemonId,
                        entryNumber = pokemon.entryNumbers.getOrDefault(pokedexId, -1),
                        name = localizedName,
                        height = pokemon.height,
                        weight = pokemon.weight,
                        sprites = sprites,
                        types = typeStates.filterNotNull()
                    )
                }
            }
        }
    }
}