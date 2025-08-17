/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SelectLocaleNav.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.08.25, 19:41
 */

package dev.bittim.encountr.onboarding.ui.screens.selectLocale

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object SelectLocaleNav

fun NavGraphBuilder.selectLocaleScreen() {
    composable<SelectLocaleNav> {
        val viewModel: SelectLocaleViewModel = koinViewModel()
        viewModel.state.collectAsStateWithLifecycle()
    }
}