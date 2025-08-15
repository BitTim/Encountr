/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Definition.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 13:01
 */

package dev.bittim.encountr.core.domain.model.defs

data class Definition(
    val game: Int,
    val pokemon: Int,
    val form: Int?
)
