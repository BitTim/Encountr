/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SelectLanguageScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   04.09.25, 18:06
 */

package dev.bittim.encountr.onboarding.ui.screens.selectLanguage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.bittim.encountr.core.di.Constants
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
fun SelectLanguageScreen(
    state: SelectLanguageState,
    onContinue: (languageName: String, navNext: () -> Unit) -> Unit,
    navNext: () -> Unit,
    navBack: () -> Unit
) {
    var selectedLocale by rememberSaveable { mutableIntStateOf(0) }

    OnboardingLayout(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding(),
        upper = { upperModifier ->
            LazyColumn(
                modifier = upperModifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(Spacing.s)
            ) {
                items(
                    state.languages?.count() ?: SelectLocaleScreenDefaults.NUM_PLACEHOLDERS
                ) { idx ->
                    val locale = state.languages?.getOrNull(idx)
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
                onContinue = {
                    onContinue(
                        state.languages?.getOrNull(selectedLocale)?.name
                            ?: Constants.DEFAULT_LANG_NAME, navNext
                    )
                },
                onBack = navBack,
                continueEnabled = state.languages != null
            )
        }
    )
}

@ScreenPreview
@Composable
fun SelectLocalScreenPreview() {
    EncountrTheme {
        Surface {
            SelectLanguageScreen(
                state = SelectLanguageState(),
                onContinue = { _, _ -> },
                navNext = {},
                navBack = {}
            )
        }
    }
}