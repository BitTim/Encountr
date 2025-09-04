/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SelectLanguageNav.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   04.09.25, 23:26
 */

package dev.bittim.encountr.onboarding.ui.screens.selectLanguage

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object SelectLocaleNav

fun NavGraphBuilder.selectLanguageScreen(
    navBack: () -> Unit,
    navNext: () -> Unit
) {
    composable<SelectLocaleNav> {
        val viewModel: SelectLanguageViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        SelectLanguageScreen(
            state = state,
            onContinue = viewModel::onContinue,
            navNext = navNext,
            navBack = navBack
        )
    }
}

fun NavController.navToOnboardingSetLanguage() {
    navigate(SelectLocaleNav) {
        launchSingleTop = true
        restoreState = true
    }
}