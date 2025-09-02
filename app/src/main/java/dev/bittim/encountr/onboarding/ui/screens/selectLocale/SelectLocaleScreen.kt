/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SelectLocaleScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   02.09.25, 04:53
 */

package dev.bittim.encountr.onboarding.ui.screens.selectLocale

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.bittim.encountr.core.ui.components.LocaleCard
import dev.bittim.encountr.core.ui.components.LocaleCardState
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.annotations.ScreenPreview
import dev.bittim.encountr.onboarding.ui.components.OnboardingActions
import dev.bittim.encountr.onboarding.ui.components.OnboardingLayout

data object SelectLocaleScreenDefaults {
    const val NUM_PLACEHOLDERS = 6
}

@Composable
fun SelectLocaleScreen(
    state: SelectLocaleState,
    navNext: () -> Unit = {},
    navBack: () -> Unit = {}
) {
    var selectedLocale by rememberSaveable { mutableIntStateOf(0) }

    OnboardingLayout(
        modifier = Modifier.fillMaxSize(),
        upper = { upperModifier ->
            LazyColumn(
                modifier = upperModifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(Spacing.s)
            ) {
                items(
                    state.locales?.count() ?: SelectLocaleScreenDefaults.NUM_PLACEHOLDERS
                ) { idx ->
                    val locale = state.locales?.getOrNull(idx)
                    val localeCardState = if (locale == null) null else {
                        LocaleCardState(
                            name = locale.names.find { it.language.name == locale.name }?.name
                                ?: locale.name,
                            countryCode = locale.iso3166
                        )
                    }

                    LocaleCard(
                        state = localeCardState,
                        isSelected = idx == selectedLocale,
                        onClick = {
                            selectedLocale = idx
                        }
                    )
                }
            }
        },
        lower = { lowerModifier ->
            OnboardingActions(
                modifier = lowerModifier.fillMaxWidth(),
                onContinue = navNext,
                onBack = navBack
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