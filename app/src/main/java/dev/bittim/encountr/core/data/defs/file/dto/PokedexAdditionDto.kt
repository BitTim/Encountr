/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexAdditionDto.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.defs.file.dto

import dev.bittim.encountr.core.data.defs.local.entity.PokedexAdditionEntity
import kotlinx.serialization.Serializable

@Serializable
data class PokedexAdditionDto(
    val versionGroupId: Int,
    val pokedexIds: List<Int>
) {
    fun toEntity(): PokedexAdditionEntity {
        return PokedexAdditionEntity(
            versionGroupId = this.versionGroupId,
            pokedexIds = this.pokedexIds
        )
    }
}