/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ResolvedConfigState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   09.09.25, 20:21
 */

package dev.bittim.encountr.core.data.config

import dev.bittim.encountr.core.domain.model.user.Save

data class ResolvedConfigState(
    val definitionsUrl: String,
    val languageName: String,
    val currentSave: Save,
)
