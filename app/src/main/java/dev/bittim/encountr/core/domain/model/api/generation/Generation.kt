/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Generation.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.domain.model.api.generation

import dev.bittim.encountr.core.domain.model.api.language.LocalizedString

data class Generation(
    val id: Int,
    val name: String,
    val localizedNames: List<LocalizedString>,
    val versionGroupIds: List<Int>
)