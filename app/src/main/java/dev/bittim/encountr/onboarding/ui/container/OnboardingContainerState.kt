/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       OnboardingContainerState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   13.08.25, 04:24
 */

package dev.bittim.encountr.onboarding.ui.container

import androidx.compose.ui.graphics.vector.ImageVector
import dev.bittim.encountr.core.ui.util.UiText

data class OnboardingContainerState(
    val route: String? = null,
    val title: UiText? = null,
    val description: UiText? = null,
    val icon: ImageVector? = null,
    val progress: Float? = null
)