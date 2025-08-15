/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionDto.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 13:01
 */

package dev.bittim.encountr.core.data.defs.remote.dto

import dev.bittim.encountr.core.data.defs.local.DefinitionEntity
import kotlinx.serialization.Serializable

@Serializable
data class DefinitionDto(
    val game: Int,
    val pokemon: Int,
    val form: Int?
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