/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LandingState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   13.08.25, 04:24
 */

package dev.bittim.encountr.onboarding.ui.screens.landing

import dev.bittim.encountr.core.ui.util.UiText

data class LandingState(
    val fetching: Boolean = false,
    val urlError: UiText? = null
)
