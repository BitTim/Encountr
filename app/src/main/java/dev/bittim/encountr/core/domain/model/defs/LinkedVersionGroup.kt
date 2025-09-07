/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LinkedVersionGroup.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.09.25, 23:49
 */

package dev.bittim.encountr.core.domain.model.defs

data class LinkedVersionGroup(
    val parent: Int,
    val linked: List<Int>
)
