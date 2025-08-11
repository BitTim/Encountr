/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionDto.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.08.25, 17:37
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
    fun toEntity(): DefinitionEntity {
        return DefinitionEntity(
            id = 0,
            game = this.game,
            pokemon = this.pokemon,
            form = this.form
        )
    }
}