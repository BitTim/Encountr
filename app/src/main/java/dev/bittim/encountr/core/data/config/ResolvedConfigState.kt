/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ResolvedConfigState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.config

import dev.bittim.encountr.core.domain.model.user.Save

data class ResolvedConfigState(
    val languageId: Int,
    val currentSave: Save,
)
