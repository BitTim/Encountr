/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   14.08.25, 03:30
 */

package dev.bittim.encountr.core.data.defs.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.domain.model.defs.Definition

@Entity(tableName = "definition")
data class DefinitionEntity(
    @PrimaryKey val id: Int,
    val game: String,
    val pokemon: String,
    val form: String?
) {
    fun toModel(): Definition {
        return Definition(
            game = this.game,
            pokemon = this.pokemon,
            form = this.form
        )
    }
}
