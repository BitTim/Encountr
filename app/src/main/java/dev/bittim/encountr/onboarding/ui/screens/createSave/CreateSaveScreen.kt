/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       CreateSaveScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   14.08.25, 03:26
 */

package dev.bittim.encountr.onboarding.ui.screens.createSave

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.util.annotations.ScreenPreview
import dev.bittim.encountr.onboarding.ui.components.OnboardingActions
import dev.bittim.encountr.onboarding.ui.components.OnboardingLayout

@Composable
fun CreateSaveScreen(
    state: CreateSaveState,
    navBack: () -> Unit,
    navNext: () -> Unit,
) {
    OnboardingLayout(
        modifier = Modifier.fillMaxSize(),
        upper = {
            Box(modifier = it) { }
        },
        lower = {
            Column(
                modifier = it
            ) {
                OnboardingActions(
                    modifier = Modifier.fillMaxWidth(),
                    onContinue = navNext,
                    onBack = navBack
                )
            }
        }
    )
}

@ScreenPreview
@Composable
fun CreateSaveScreenPreview() {
    EncountrTheme {
        Surface {
            CreateSaveScreen(
                state = CreateSaveState(),
                navBack = {},
                navNext = {}
            )
        }
    }
}