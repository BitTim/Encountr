/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       CreateSaveNav.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.08.25, 21:15
 */

package dev.bittim.encountr.onboarding.ui.screens.createSave

import android.util.Log
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object CreateSaveNav

fun NavGraphBuilder.createSaveScreen(
    navBack: () -> Unit
) {
    composable<CreateSaveNav> {
        val viewModel = koinViewModel<CreateSaveViewModel>()
        val state = viewModel.state.collectAsStateWithLifecycle()

        CreateSaveScreen(
            state = state.value,
            loadNext = viewModel::loadNext,
            navBack = navBack,
            navNext = { Log.d("CreateSaveScreen", "Next") }
        )
    }
}

fun NavController.navToOnboardingCreateSave() {
    navigate(CreateSaveNav) {
        launchSingleTop = true
        restoreState = true
    }
}