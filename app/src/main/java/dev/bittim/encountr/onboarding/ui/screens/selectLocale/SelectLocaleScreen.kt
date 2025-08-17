/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SelectLocaleScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.08.25, 19:41
 */

package dev.bittim.encountr.onboarding.ui.screens.selectLocale

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.util.annotations.ScreenPreview
import dev.bittim.encountr.onboarding.ui.components.OnboardingActions
import dev.bittim.encountr.onboarding.ui.components.OnboardingLayout

@Composable
fun SelectLocaleScreen(
    state: SelectLocaleState,
) {
    val pagerState = rememberPagerState(pageCount = { state.locales.size })

    OnboardingLayout(
        modifier = Modifier.fillMaxSize(),
        upper = {
            VerticalPager(
                modifier = Modifier.fillMaxSize(),
                state = pagerState
            ) {

            }
        },
        lower = {
            OnboardingActions(
                modifier = Modifier.fillMaxWidth(),
                onContinue = { /*TODO*/ },
                onBack = { /*TODO*/ }
            )
        }
    )
}

@ScreenPreview
@Composable
fun SelectLocalScreenPreview() {
    EncountrTheme {
        Surface {
            SelectLocaleScreen(
                state = SelectLocaleState()
            )
        }
    }
}