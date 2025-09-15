/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DamageRelations.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.09.25, 00:53
 */

package dev.bittim.encountr.core.domain.model.pokeapi

data class DamageRelations(
    val doubleFrom: List<Int>,
    val doubleTo: List<Int>,
    val halfFrom: List<Int>,
    val halfTo: List<Int>,
    val noFrom: List<Int>,
    val noTo: List<Int>
)
