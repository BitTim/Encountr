/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionDto.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.defs.file.dto

import dev.bittim.encountr.core.data.defs.local.entity.DefinitionEntity
import kotlinx.serialization.Serializable

@Serializable
data class DefinitionDto(
    val version: Int,
    val imageUrl: String?,
    val ignoredVersionIds: List<Int>,
    val pokedexAdditions: List<PokedexAdditionDto>,
    val versionAdditions: List<VersionAdditionDto>
) {
    fun toEntity(): DefinitionEntity {
        return DefinitionEntity(
            imageUrl = this.imageUrl,
            ignoredVersionIds = this.ignoredVersionIds
        )
    }
}