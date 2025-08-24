/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SelectLocaleNav.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   24.08.25, 20:10
 */

package dev.bittim.encountr.onboarding.ui.screens.selectLocale

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object SelectLocaleNav

fun NavGraphBuilder.selectLocaleScreen(
    navNext: () -> Unit,
    navBack: () -> Unit
) {
    composable<SelectLocaleNav> {
        val viewModel: SelectLocaleViewModel = koinViewModel()
        val state = viewModel.state.collectAsStateWithLifecycle()

        SelectLocaleScreen(
            state = state.value,
            navNext = navNext,
            navBack = navBack
        )
    }
}

fun NavController.navToOnboardingSetLocale() {
    navigate(SelectLocaleNav) {
        launchSingleTop = true
        restoreState = true
    }
}