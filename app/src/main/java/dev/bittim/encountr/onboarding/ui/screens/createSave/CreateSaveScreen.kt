/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       CreateSaveScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.onboarding.ui.screens.createSave

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import dev.bittim.encountr.R
import dev.bittim.encountr.core.domain.model.api.Handle
import dev.bittim.encountr.core.ui.components.GameCardDefaults
import dev.bittim.encountr.core.ui.components.LabeledSlider
import dev.bittim.encountr.core.ui.components.VersionCard
import dev.bittim.encountr.core.ui.components.VersionCardState
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.UiText
import dev.bittim.encountr.core.ui.util.annotations.ScreenPreview
import dev.bittim.encountr.core.ui.util.extenstions.modifier.SATURATION_DESATURATED
import dev.bittim.encountr.core.ui.util.extenstions.modifier.saturation
import dev.bittim.encountr.onboarding.ui.components.OnboardingActions
import dev.bittim.encountr.onboarding.ui.components.OnboardingLayout
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateSaveScreen(
    state: CreateSaveState,
    onGenChanged: (generationId: Int) -> Unit,
    onContinue: (gameId: Int, name: String, navNext: () -> Unit) -> Unit,
    navBack: () -> Unit,
    navNext: () -> Unit,
) {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current

    val pagerState by remember(state.versions) {
        mutableStateOf(
            PagerState(
                currentPage = 0,
                pageCount = { if (state.versions.isNotEmpty()) state.versions.count() else 3 },
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

    var clickedPage by remember { mutableIntStateOf(0) }

    LaunchedEffect(sliderState.value) {
        onGenChanged(sliderState.value.toInt() + 1)
    }

    LaunchedEffect(clickedPage) {
        pagerState.animateScrollToPage(clickedPage)
    }

    OnboardingLayout(
        modifier = Modifier.fillMaxSize(),
        upper = { upperModifier ->
            Box(
                modifier = upperModifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val pageSize = GameCardDefaults.iconSize
                val contentPadding =
                    with(density) { (windowInfo.containerSize.width.toDp() - pageSize) / 2 }

                HorizontalPager(
                    modifier = upperModifier,
                    userScrollEnabled = state.versions.isNotEmpty(),
                    state = pagerState,
                    pageSize = PageSize.Fixed(pageSize),
                    pageSpacing = Spacing.s,
                    contentPadding = PaddingValues(horizontal = contentPadding),
                    flingBehavior = PagerDefaults.flingBehavior(
                        state = pagerState,
                        pagerSnapDistance = PagerSnapDistance.atMost(pagerState.pageCount)
                    ),
                ) { index ->
                    if (index >= state.versions.size) return@HorizontalPager

                    val pageOffset = pagerState.getOffsetDistanceInPages(index).absoluteValue
                    val topPadding = (32f * minOf(pageOffset, 1f)).dp
                    val bottomPadding = 32.dp - topPadding
                    val blur =
                        lerp(start = 0f, stop = 4f, fraction = pageOffset.coerceIn(0f, 1f)).dp
                    val saturation = lerp(
                        start = SATURATION_DESATURATED,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )

                    val versionCardState = state.versions[index]?.let { version ->
                        state.languageId?.let { langName ->
                            VersionCardState(version, Handle(0))
                        }
                    }

                    VersionCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .blur(blur)
                            .padding(
                                start = Spacing.xs,
                                end = Spacing.xs,
                                top = Spacing.xs + topPadding,
                                bottom = Spacing.xs + bottomPadding
                            )
                            .saturation(saturation)
                            .graphicsLayer {
                                alpha = lerp(
                                    start = 0f,
                                    stop = 1f,
                                    fraction = 2f - pageOffset.coerceIn(0f, 2f)
                                )
                            }
                            .clickable(
                                enabled = state.versions.isNotEmpty(),
                            ) {
                                clickedPage = index
                            },
                        state = versionCardState
                    )
                }
            }
        },
        lower = { lowerModifier ->
            Column(
                modifier = lowerModifier.safeContentPadding(),
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
                    onContinue = {
                        onContinue(
                            state.versions[pagerState.currentPage]?.id ?: 0,
                            textFieldState.text.toString(),
                            navNext
                        )
                    },
                    onBack = navBack,
                    continueEnabled = state.versions.isNotEmpty() && textFieldState.text.isNotEmpty()
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
                onContinue = { _, _, _ -> },
                navBack = {},
                navNext = {}
            )
        }
    }
}