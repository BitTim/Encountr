/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LinkedVersionGroupDto.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.09.25, 23:54
 */

package dev.bittim.encountr.core.data.defs.remote.dto

import dev.bittim.encountr.core.data.defs.local.entity.LinkedVersionGroupEntity
import kotlinx.serialization.Serializable

@Serializable
data class LinkedVersionGroupDto(
    val parent: Int,
    val linked: List<Int>
) {
    fun toEntity(index: Int): LinkedVersionGroupEntity {
        return LinkedVersionGroupEntity(
            id = index,
            parent = this.parent,
            linked = this.linked
        )
    }
}