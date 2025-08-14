/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       OnboardingModule.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   14.08.25, 03:17
 */

package dev.bittim.encountr.onboarding.di

import dev.bittim.encountr.onboarding.ui.container.OnboardingContainerViewModel
import dev.bittim.encountr.onboarding.ui.screens.createSave.CreateSaveViewModel
import dev.bittim.encountr.onboarding.ui.screens.landing.LandingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val onboardingModule = module {
    // region:      -- ViewModels

    viewModelOf(::OnboardingContainerViewModel)
    viewModelOf(::LandingViewModel)
    viewModelOf(::CreateSaveViewModel)

    // endregion:   -- ViewModels
}