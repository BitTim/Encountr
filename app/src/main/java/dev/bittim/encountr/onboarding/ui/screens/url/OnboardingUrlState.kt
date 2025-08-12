/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       OnboardingUrlState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   12.08.25, 01:11
 */

package dev.bittim.encountr.onboarding.ui.screens.url

import dev.bittim.encountr.core.ui.util.UiText

data class OnboardingUrlState(
    val fetching: Boolean = false,
    val urlError: UiText? = null
)
