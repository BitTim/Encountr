/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionDto.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   14.08.25, 03:28
 */

package dev.bittim.encountr.core.data.defs.remote.dto

import dev.bittim.encountr.core.data.defs.local.DefinitionEntity
import kotlinx.serialization.Serializable

@Serializable
data class DefinitionDto(
    val game: String,
    val pokemon: String,
    val form: String?
) {
    fun toEntity(index: Int): DefinitionEntity {
        return DefinitionEntity(
            id = index,
            game = this.game,
            pokemon = this.pokemon,
            form = this.form
        )
    }
}