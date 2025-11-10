/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Type.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.domain.model.api.type

import dev.bittim.encountr.core.domain.model.api.language.LocalizedString

data class Type(
    val id: Int,
    val name: String,
    val localizedNames: List<LocalizedString>,
    val sprites: List<TypeSprite>,

    val doubleDamageFrom: List<Int>,
    val halfDamageFrom: List<Int>,
    val noDamageFrom: List<Int>,

    val doubleDamageTo: List<Int>,
    val halfDamageTo: List<Int>,
    val noDamageTo: List<Int>
)
