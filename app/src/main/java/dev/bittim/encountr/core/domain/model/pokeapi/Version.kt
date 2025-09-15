/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Version.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.09.25, 18:03
 */

package dev.bittim.encountr.core.domain.model.pokeapi

data class Version(
    val id: Int,
    val name: String,
    val localizedNames: List<LocalizedString>,
    val generationName: String,
    val localizedGenerationNames: List<LocalizedString>,
    val versionGroupId: Int,
    val imageUrl: String?
)
