/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Type.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.09.25, 00:53
 */

package dev.bittim.encountr.core.domain.model.pokeapi

import co.pokeapi.pokekotlin.model.VersionTypeSprites

data class Type(
    val id: Int,
    val name: String,
    val localizedNames: List<LocalizedString>,
    val damageRelations: DamageRelations,
    val sprites: VersionTypeSprites
)
