/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       CreateSaveNav.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.11.25, 02:34
 */

package dev.bittim.encountr.onboarding.ui.screens.createSave

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object CreateSaveNav

fun NavGraphBuilder.createSaveScreen(
    navBack: () -> Unit,
    navNext: () -> Unit
) {
    composable<CreateSaveNav> {
        val viewModel = koinViewModel<CreateSaveViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        CreateSaveScreen(
            state = state,
            onGenChanged = viewModel::onGenChanged,
            observeVersion = viewModel::observeVersion,
            onContinue = viewModel::onContinue,
            navBack = navBack,
            navNext = navNext
        )
    }
}

fun NavController.navToOnboardingCreateSave() {
    navigate(CreateSaveNav) {
        launchSingleTop = true
        restoreState = true
    }
}