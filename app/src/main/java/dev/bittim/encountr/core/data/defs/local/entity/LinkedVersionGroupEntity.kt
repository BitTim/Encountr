/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LinkedVersionGroupEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.09.25, 23:54
 */

package dev.bittim.encountr.core.data.defs.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.bittim.encountr.core.data.defs.local.converter.IntListConverter
import dev.bittim.encountr.core.domain.model.defs.LinkedVersionGroup

@Entity(
    tableName = "linked_version_group",
    indices = [
        Index(value = ["parent"], unique = true)
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
@TypeConverters(IntListConverter::class)
data class LinkedVersionGroupEntity(
    @PrimaryKey val id: Int,
    val definition: Int = 0,
    val parent: Int,
    val linked: List<Int>,
) {
    fun toModel(): LinkedVersionGroup {
        return LinkedVersionGroup(
            parent = this.parent,
            linked = this.linked
        )
    }
}
