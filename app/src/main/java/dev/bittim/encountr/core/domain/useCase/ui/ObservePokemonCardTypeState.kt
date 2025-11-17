/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ObservePokemonCardTypeState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 20:29
 */

package dev.bittim.encountr.core.domain.useCase.ui

import dev.bittim.encountr.content.ui.components.PokemonCardTypeState
import dev.bittim.encountr.core.data.api.repo.type.TypeRepository
import dev.bittim.encountr.core.domain.model.api.type.TypeSpriteVariant
import dev.bittim.encountr.core.domain.useCase.util.ObserveLocalizedName
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ObservePokemonCardTypeState(
    private val observeLocalizedName: ObserveLocalizedName,
    private val typeRepository: TypeRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(
        typeId: Int,
        spriteVariant: TypeSpriteVariant?
    ): Flow<PokemonCardTypeState?> {
        return typeRepository.get(typeId).flatMapLatest { type ->
            if (type != null) observeLocalizedName(
                type.localizedNames,
                type.name
            ).map { localizedName ->
                PokemonCardTypeState(
                    name = localizedName,
                    imageUrl = type.sprites.find { it.variant == spriteVariant }?.imageUrl
                )
            }
            else flowOf(null)
        }
    }
}