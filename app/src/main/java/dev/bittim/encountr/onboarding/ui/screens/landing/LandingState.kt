/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LandingState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   06.09.25, 02:27
 */

package dev.bittim.encountr.onboarding.ui.screens.landing

import dev.bittim.encountr.core.ui.util.UiText

data class LandingState(
    val imageUrl: String? = null,
    val lastValidUrl: String? = null,
    val fetching: Boolean = false,
    val urlError: UiText? = null
)
