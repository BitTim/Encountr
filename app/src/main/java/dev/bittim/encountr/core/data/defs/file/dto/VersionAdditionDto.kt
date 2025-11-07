/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionAdditionDto.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.defs.file.dto

import dev.bittim.encountr.core.data.defs.local.entity.VersionAdditionEntity
import dev.bittim.encountr.core.domain.model.api.pokemon.PokemonSpriteVariant
import dev.bittim.encountr.core.domain.model.api.type.TypeSpriteVariant
import kotlinx.serialization.Serializable

@Serializable
data class VersionAdditionDto(
    val versionId: Int,
    val imageUrl: String?,
    val pokemonSpriteVariant: String?,
    val typeSpriteVariant: String?
) {
    fun toEntity(): VersionAdditionEntity {
        return VersionAdditionEntity(
            versionId = this.versionId,
            imageUrl = this.imageUrl,
            pokemonSpriteVariant = this.pokemonSpriteVariant?.let { PokemonSpriteVariant.valueOf(it) },
            typeSpriteVariant = this.typeSpriteVariant?.let { TypeSpriteVariant.valueOf(it) }
        )
    }
}