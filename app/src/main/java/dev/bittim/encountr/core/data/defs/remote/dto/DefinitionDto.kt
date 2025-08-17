/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionDto.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.08.25, 03:13
 */

package dev.bittim.encountr.core.data.defs.remote.dto

import dev.bittim.encountr.core.data.defs.local.entity.DefinitionEntity
import kotlinx.serialization.Serializable

@Serializable
data class DefinitionDto(
    val version: Int,
    val ignored: List<Int>,
    val icons: List<IconDefinitionDto>
) {
    fun toEntity(): DefinitionEntity {
        return DefinitionEntity(
            ignored = this.ignored
        )
    }
}