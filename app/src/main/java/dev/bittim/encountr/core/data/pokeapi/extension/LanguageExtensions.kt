/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LanguageExtensions.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   31.08.25, 16:55
 */

package dev.bittim.encountr.core.data.pokeapi.extension

import co.pokeapi.pokekotlin.model.Language

fun Language.hasSelfLocalizedName(): Boolean {
    return names.any { it.language.name == name }
}