/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 13:01
 */

package dev.bittim.encountr.core.data.defs.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.domain.model.defs.Definition

@Entity(tableName = "definition")
data class DefinitionEntity(
    @PrimaryKey val id: Int,
    val game: Int,
    val pokemon: Int,
    val form: Int?
) {
    fun toModel(): Definition {
        return Definition(
            game = this.game,
            pokemon = this.pokemon,
            form = this.form
        )
    }
}
