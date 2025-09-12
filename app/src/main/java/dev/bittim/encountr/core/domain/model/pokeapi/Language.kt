/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Language.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   12.09.25, 16:47
 */

package dev.bittim.encountr.core.domain.model.pokeapi

data class Language(
    val id: Int,
    val name: String,
    val localizedName: String,
    val countryCode: String,
)
