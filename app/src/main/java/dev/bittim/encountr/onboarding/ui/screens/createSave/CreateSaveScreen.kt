/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       CreateSaveScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.11.25, 02:34
 */

package dev.bittim.encountr.onboarding.ui.screens.createSave

import androidx.compose.animation.Crossfade
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
import androidx.compose.material3.CircularProgressIndicator
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
import dev.bittim.encountr.core.ui.components.general.LabeledSlider
import dev.bittim.encountr.core.ui.components.version.versionCard.VersionCard
import dev.bittim.encountr.core.ui.components.version.versionCard.VersionCardDefaults
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
    observeVersion: (versionId: Int) -> Unit,
    onContinue: (versionId: Int, name: String, navNext: () -> Unit) -> Unit,
    navBack: () -> Unit,
    navNext: () -> Unit,
) {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current

    var generationId by remember { mutableIntStateOf(1) }
    val versionIds = state.versionIds[generationId]
    var versionId by remember { mutableIntStateOf(1) }

    val pagerState by remember(versionIds) {
        mutableStateOf(
            PagerState(
                currentPage = 0,
                pageCount = { if (!versionIds.isNullOrEmpty()) versionIds.count() else 3 },
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
        generationId = sliderState.value.toInt() + 1
        onGenChanged(generationId)
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
                val pageSize = VersionCardDefaults.iconSize
                val contentPadding =
                    with(density) { (windowInfo.containerSize.width.toDp() - pageSize) / 2 }

                Crossfade(
                    modifier = upperModifier,
                    targetState = versionIds
                ) { versionIds ->
                    if (versionIds == null) {
                        CircularProgressIndicator()
                        return@Crossfade
                    }

                    HorizontalPager(
                        userScrollEnabled = versionIds.isNotEmpty(),
                        state = pagerState,
                        pageSize = PageSize.Fixed(pageSize),
                        pageSpacing = Spacing.s,
                        contentPadding = PaddingValues(horizontal = contentPadding),
                        beyondViewportPageCount = 2,
                        flingBehavior = PagerDefaults.flingBehavior(
                            state = pagerState,
                            pagerSnapDistance = PagerSnapDistance.atMost(pagerState.pageCount)
                        ),
                    ) { index ->
                        if (index >= versionIds.size) return@HorizontalPager

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

                        versionId = versionIds[index]
                        observeVersion(versionId)

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
                                    enabled = versionIds.isNotEmpty(),
                                ) {
                                    clickedPage = index
                                },
                            state = state.versionStates[versionId]
                        )
                    }
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
                            versionId,
                            textFieldState.text.toString(),
                            navNext
                        )
                    },
                    onBack = navBack,
                    continueEnabled = state.versionIds.isNotEmpty() && textFieldState.text.isNotEmpty()
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
                observeVersion = {},
                onContinue = { _, _, _ -> },
                navBack = {},
                navNext = {}
            )
        }
    }
}