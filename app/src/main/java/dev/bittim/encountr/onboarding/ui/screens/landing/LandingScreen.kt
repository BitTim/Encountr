/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LandingScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.onboarding.ui.screens.landing

import android.content.Intent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import dev.bittim.encountr.R
import dev.bittim.encountr.core.di.Constants
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.UiText
import dev.bittim.encountr.core.ui.util.annotations.ScreenPreview
import dev.bittim.encountr.core.ui.util.extenstions.modifier.pulseAnimation
import dev.bittim.encountr.onboarding.ui.components.OnboardingLayout

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LandingScreen(
    state: LandingState,
    onContinue: (navNext: () -> Unit) -> Unit,
    navNext: () -> Unit
) {
    val context = LocalContext.current
    LocalDensity.current
    LocalConfiguration.current

    OnboardingLayout(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding(),
        upper = { upperModifier ->
            Crossfade(
                modifier = upperModifier,
                targetState = state.imageUrl,
            ) {
                if (it != null) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = it,
                        contentScale = ContentScale.Fit,
                        contentDescription = null,
                        filterQuality = FilterQuality.None
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(MaterialTheme.shapes.medium)
                            .pulseAnimation()
                    )
                }
            }
        },
        lower = { lowerModifier ->
            Column(
                modifier = lowerModifier,
                verticalArrangement = Arrangement.spacedBy(Spacing.xl)
            ) {
                // region:      -- Actions
                Column {
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { }
                    ) {
                        Icon(
                            Icons.Default.PrivacyTip,
                            UiText.StringResource(R.string.content_desc_privacy_policy)
                                .asString()
                        )
                        Spacer(modifier = Modifier.width(Spacing.s))
                        Text(
                            text = UiText.StringResource(R.string.button_privacy_policy)
                                .asString()
                        )
                    }

                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            val intent =
                                Intent(Intent.ACTION_VIEW, Constants.GITHUB_URL.toUri())
                            context.startActivity(intent)
                        }
                    ) {
                        Icon(
                            Icons.Default.Code,
                            UiText.StringResource(R.string.content_desc_github).asString()
                        )
                        Spacer(modifier = Modifier.width(Spacing.s))
                        Text(text = UiText.StringResource(R.string.button_github).asString())
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onContinue(navNext) },
                    ) {
                        Text(
                            text = UiText.StringResource(R.string.button_continue)
                                .asString()
                        )
                    }
                }
                // endregion:   -- Actions
            }
        }
    )
}

@ScreenPreview
@Composable
fun LandingScreenPreview() {
    EncountrTheme {
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Spacing.m),
            ) {
                LandingScreen(
                    state = LandingState(),
                    onContinue = { },
                    navNext = { }
                )
            }
        }
    }
}