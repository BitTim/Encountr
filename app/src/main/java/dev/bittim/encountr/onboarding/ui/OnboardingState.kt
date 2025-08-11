/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       OnboardingState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.08.25, 18:04
 */

package dev.bittim.encountr.onboarding.ui

import dev.bittim.encountr.core.ui.util.UiText

data class OnboardingState(
    val fetching: Boolean = false,
    val urlError: UiText? = null
)
