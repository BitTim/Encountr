/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SelectLanguageScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.11.25, 01:10
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.bittim.encountr.core.di.Constants
import dev.bittim.encountr.core.ui.components.language.languageCard.LanguageCard
import dev.bittim.encountr.core.ui.components.language.languageCard.LanguageCardState
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.annotations.ScreenPreview
import dev.bittim.encountr.onboarding.ui.components.OnboardingActions
import dev.bittim.encountr.onboarding.ui.components.OnboardingLayout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data object SelectLocaleScreenDefaults {
    const val NUM_PLACEHOLDERS = 6
}

@Composable
fun SelectLanguageScreen(
    state: SelectLanguageState,
    observeLanguage: (languageId: Int?) -> Flow<LanguageCardState?>,
    onContinue: (languageId: Int, navNext: () -> Unit) -> Unit,
    navNext: () -> Unit,
    navBack: () -> Unit
) {
    var selectedIdx by rememberSaveable { mutableIntStateOf(0) }

    OnboardingLayout(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding(),
        upper = { upperModifier ->
            LazyColumn(
                modifier = upperModifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(Spacing.s)
            ) {
                val count =
                    if (state.languageIds.isNotEmpty()) state.languageIds.count() else SelectLocaleScreenDefaults.NUM_PLACEHOLDERS
                items(count) { idx ->
                    val languageId = state.languageIds.getOrNull(idx)
                    val languageCardState by remember(languageId) { observeLanguage(languageId) }.collectAsStateWithLifecycle(
                        null
                    )

                    LanguageCard(
                        modifier = Modifier.animateItem(),
                        state = languageCardState,
                        isSelected = idx == selectedIdx,
                        onClick = {
                            selectedIdx = idx
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
                        state.languageIds.getOrNull(selectedIdx)
                            ?: Constants.DEFAULT_LANG_ID, navNext
                    )
                },
                onBack = navBack,
                continueEnabled = state.languageIds.isNotEmpty()
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
                observeLanguage = { flowOf(null) },
                onContinue = { _, _ -> },
                navNext = {},
                navBack = {}
            )
        }
    }
}