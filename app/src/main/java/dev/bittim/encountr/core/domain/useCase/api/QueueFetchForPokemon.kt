/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       QueueFetchForPokemon.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 23:54
 */

package dev.bittim.encountr.core.domain.useCase.api

import dev.bittim.encountr.core.data.api.repo.pokemon.PokemonRepository

class QueueFetchForPokemon(
    private val pokemonRepository: PokemonRepository
) {
    operator fun invoke(pokemonIds: List<Int>) {
        pokemonIds.forEach { pokemonId ->
            pokemonRepository.queueWorker(pokemonId)
        }
    }
}