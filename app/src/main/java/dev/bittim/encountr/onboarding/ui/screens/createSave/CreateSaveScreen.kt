/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       CreateSaveScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   02.09.25, 18:49
 */

package dev.bittim.encountr.onboarding.ui.screens.createSave

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SliderState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import dev.bittim.encountr.R
import dev.bittim.encountr.core.ui.components.GameCard
import dev.bittim.encountr.core.ui.components.GameCardDefaults
import dev.bittim.encountr.core.ui.components.LabeledSlider
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.UiText
import dev.bittim.encountr.core.ui.util.annotations.ScreenPreview
import dev.bittim.encountr.onboarding.ui.components.OnboardingActions
import dev.bittim.encountr.onboarding.ui.components.OnboardingLayout

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateSaveScreen(
    state: CreateSaveState,
    onGenChanged: (generationId: Int) -> Unit,
    navBack: () -> Unit,
    navNext: () -> Unit,
) {
    val pagerState by remember(state.games) {
        mutableStateOf(
            PagerState(
                currentPage = 0,
                pageCount = { state.games?.count() ?: 3 },
            )
        )
    }
    val sliderState by remember(state.generations) {
        mutableStateOf(
            SliderState(
                valueRange = 0f..(state.generations?.minus(1)?.toFloat() ?: 0f),
                steps = state.generations?.minus(2) ?: 0,
            )
        )
    }
    val textFieldState = rememberTextFieldState()

    LaunchedEffect(sliderState.value) {
        onGenChanged(sliderState.value.toInt() + 1)
    }

    OnboardingLayout(
        modifier = Modifier.fillMaxSize(),
        upper = { upperModifier ->
            HorizontalPager(
                modifier = upperModifier,
                state = pagerState,
                pageSize = PageSize.Fixed(GameCardDefaults.iconSize),
                pageSpacing = Spacing.m
            ) { index ->
                state.games?.get(index)?.toGameSelectorState()?.let {
                    GameCard(
                        modifier = Modifier.fillMaxWidth(),
                        state = it
                    )
                }
            }
        },
        lower = { lowerModifier ->
            Column(
                modifier = lowerModifier,
                verticalArrangement = Arrangement.spacedBy(Spacing.l)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(Spacing.s)
                ) {
                    LabeledSlider(
                        modifier = Modifier.fillMaxWidth(),
                        label = UiText.StringResource(R.string.onboarding_create_save_gen_slider_label),
                        sliderState = sliderState
                    )

                    HorizontalDivider()

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        state = textFieldState,
                        label = {
                            Text(
                                text = UiText.StringResource(R.string.onboarding_create_save_name_field_label)
                                    .asString()
                            )
                        },
                        lineLimits = TextFieldLineLimits.SingleLine
                    )
                }

                OnboardingActions(
                    modifier = Modifier.fillMaxWidth(),
                    onContinue = navNext,
                    onBack = navBack,
                    continueEnabled = false
                )
            }
        },
    )
}

@ScreenPreview
@Composable
fun CreateSaveScreenPreview() {
    EncountrTheme {
        Surface {
            CreateSaveScreen(
                state = CreateSaveState(),
                onGenChanged = {},
                navBack = {},
                navNext = {}
            )
        }
    }
}