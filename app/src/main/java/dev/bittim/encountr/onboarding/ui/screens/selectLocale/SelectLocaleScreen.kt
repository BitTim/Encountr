/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SelectLocaleScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   31.08.25, 17:03
 */

package dev.bittim.encountr.onboarding.ui.screens.selectLocale

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import dev.bittim.encountr.core.ui.components.LocaleCard
import dev.bittim.encountr.core.ui.components.LocaleCardDefaults
import dev.bittim.encountr.core.ui.components.LocaleCardState
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.annotations.ScreenPreview
import dev.bittim.encountr.core.ui.util.extenstions.modifier.SATURATION_DESATURATED
import dev.bittim.encountr.core.ui.util.extenstions.modifier.saturation
import dev.bittim.encountr.onboarding.ui.components.OnboardingActions
import dev.bittim.encountr.onboarding.ui.components.OnboardingLayout
import kotlin.math.absoluteValue

@Composable
fun SelectLocaleScreen(
    state: SelectLocaleState,
    navNext: () -> Unit = {},
    navBack: () -> Unit = {}
) {
    val pagerState = rememberPagerState(pageCount = { state.locales?.size ?: 3 })

    OnboardingLayout(
        modifier = Modifier.fillMaxSize(),
        upper = { upperModifier ->
            Box(
                modifier = upperModifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val pageSize = LocaleCardDefaults.height + (Spacing.xs * 2)

                VerticalPager(
                    modifier = Modifier
                        .height(pageSize * 5)
                        .align(Alignment.Center),
                    state = pagerState,
                    pageSize = PageSize.Fixed(pageSize),
                    contentPadding = PaddingValues(vertical = pageSize * 2),
                    flingBehavior = PagerDefaults.flingBehavior(
                        state = pagerState,
                        pagerSnapDistance = PagerSnapDistance.atMost(pagerState.pageCount)
                    ),
                ) { index ->
                    val locale = state.locales?.getOrNull(index)
                    val localeCardState = if (locale == null) null else {
                        LocaleCardState(
                            name = locale.names.find { it.language.name == locale.name }?.name
                                ?: locale.name,
                            countryCode = locale.iso3166
                        )
                    }

                    val pageOffset = pagerState.getOffsetDistanceInPages(index).absoluteValue
                    val horizontalPadding = (32f * minOf(pageOffset, 1f)).dp
                    val blur =
                        lerp(
                            start = 0f,
                            stop = 4f,
                            fraction = minOf(pageOffset, 1f).coerceIn(0f, 1f)
                        ).dp
                    val saturation = lerp(
                        start = SATURATION_DESATURATED,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )

                    LocaleCard(
                        modifier = Modifier
                            .blur(blur)
                            .padding(horizontalPadding, Spacing.xs)
                            .saturation(saturation)
                            .graphicsLayer {
                                alpha = lerp(
                                    start = 0f,
                                    stop = 1f,
                                    fraction = 2f - pageOffset.coerceIn(0f, 2f)
                                )
                            }
                            .clickable {
                                //if (pageOffset <= 1) clickedPage = it
                            }
                            .clip(MaterialTheme.shapes.medium),
                        state = localeCardState,
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