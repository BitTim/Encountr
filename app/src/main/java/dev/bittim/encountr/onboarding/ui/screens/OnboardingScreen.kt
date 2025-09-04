/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       OnboardingScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   04.09.25, 18:06
 */

package dev.bittim.encountr.onboarding.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.WavingHand
import androidx.compose.ui.graphics.vector.ImageVector
import dev.bittim.encountr.R
import dev.bittim.encountr.core.ui.util.UiText
import dev.bittim.encountr.onboarding.ui.screens.createSave.CreateSaveNav
import dev.bittim.encountr.onboarding.ui.screens.landing.LandingNav
import dev.bittim.encountr.onboarding.ui.screens.selectLanguage.SelectLocaleNav

enum class OnboardingScreen(
    val route: String,
    val title: UiText,
    val description: UiText,
    val icon: ImageVector,
    val step: Int,
) {
    Landing(
        route = LandingNav::class.java.name,
        title = UiText.StringResource(R.string.onboarding_landing_title),
        description = UiText.StringResource(R.string.onboarding_landing_description),
        icon = Icons.Default.WavingHand,
        step = 0
    ),

    SelectLocale(
        route = SelectLocaleNav::class.java.name,
        title = UiText.StringResource(R.string.onboarding_select_locale_title),
        description = UiText.StringResource(R.string.onboarding_select_locale_description),
        icon = Icons.Default.Language,
        step = 1
    ),

    CreateSave(
        route = CreateSaveNav::class.java.name,
        title = UiText.StringResource(R.string.onboarding_create_save_title),
        description = UiText.StringResource(R.string.onboarding_create_save_description),
        icon = Icons.Default.Save,
        step = 2
    );

    companion object {
        fun getMaxStep(): Int {
            return entries.maxOf { it.step }
        }
    }
}