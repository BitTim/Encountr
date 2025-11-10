/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionGroup.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.domain.model.api.versionGroup

data class VersionGroup(
    val id: Int,
    val name: String,
    val generationId: Int?,
    val versionIds: List<Int>,
    val pokedexIds: List<Int>,
)