/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       IconDefinitionDto.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.08.25, 03:10
 */

package dev.bittim.encountr.core.data.defs.remote.dto

import dev.bittim.encountr.core.data.defs.local.entity.IconDefinitionEntity
import kotlinx.serialization.Serializable

@Serializable
data class IconDefinitionDto(
    val game: Int,
    val pokemon: Int
) {
    fun toEntity(index: Int): IconDefinitionEntity {
        return IconDefinitionEntity(
            id = index,
            game = this.game,
            pokemon = this.pokemon
        )
    }
}