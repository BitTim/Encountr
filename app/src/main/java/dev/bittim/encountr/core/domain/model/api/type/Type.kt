/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Type.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.domain.model.api.type

import dev.bittim.encountr.core.domain.model.api.Handleable
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
) : Handleable
