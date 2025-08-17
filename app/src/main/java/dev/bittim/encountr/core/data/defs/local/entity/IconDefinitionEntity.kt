/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       IconDefinitionEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.08.25, 03:14
 */

package dev.bittim.encountr.core.data.defs.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.domain.model.defs.IconDefinition

@Entity(
    tableName = "icons",
    indices = [
        Index(value = ["game"], unique = true)
    ],
    foreignKeys = [
        ForeignKey(
            entity = DefinitionEntity::class,
            parentColumns = ["id"],
            childColumns = ["definition"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class IconDefinitionEntity(
    @PrimaryKey val id: Int,
    val definition: Int = 0,
    val game: Int,
    val pokemon: Int
) {
    fun toModel(): IconDefinition {
        return IconDefinition(
            game = this.game,
            pokemon = this.pokemon
        )
    }
}
