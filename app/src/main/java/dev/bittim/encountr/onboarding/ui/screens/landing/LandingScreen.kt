/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LandingScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   03.09.25, 03:11
 */

package dev.bittim.encountr.onboarding.ui.screens.landing

import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import dev.bittim.encountr.R
import dev.bittim.encountr.core.di.Constants
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.UiText
import dev.bittim.encountr.core.ui.util.annotations.ScreenPreview
import dev.bittim.encountr.onboarding.ui.components.OnboardingLayout
import kotlin.random.Random

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LandingScreen(
    state: LandingState,
    resetError: () -> Unit,
    onContinue: (
        urlString: String,
        navNext: () -> Unit
    ) -> Unit,
    navNext: () -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var definitionsUrl by rememberSaveable { mutableStateOf(Constants.DEFAULT_DEFS_URL) }
    var enableResetButton by remember { mutableStateOf(false) }
    val index by remember { mutableIntStateOf(Random.nextInt(1, 1026)) } // TODO: Temporary

    OnboardingLayout(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding(),
        upper = {
            // TODO: Temporary
            AsyncImage(
                modifier = it,
                model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$index.png",
                contentScale = ContentScale.Fit,
                contentDescription = null
            )
        },
        lower = { modifier ->
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(Spacing.xl)
            ) {
                // region:      -- Form
                Column(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onFocusChanged {
                                        if (it.isFocused) enableResetButton = true
                                    },
                                value = definitionsUrl,
                                singleLine = true,
                                isError = state.urlError != null,
                                enabled = !state.fetching,
                                onValueChange = {
                                    if (state.urlError != null) resetError()
                                    definitionsUrl = it
                                },
                                label = {
                                    Text(
                                        text = UiText.StringResource(R.string.text_field_definitions_url)
                                            .asString()
                                    )
                                },
                                trailingIcon = {
                                    IconButton(
                                        enabled = enableResetButton,
                                        onClick = {
                                            definitionsUrl = Constants.DEFAULT_DEFS_URL
                                            enableResetButton = false
                                            focusManager.clearFocus()
                                            resetError()
                                        }
                                    ) {
                                        Icon(
                                            Icons.Default.RestartAlt,
                                            UiText.StringResource(R.string.content_desc_reset)
                                                .asString()
                                        )
                                    }
                                }
                            )

                            AnimatedVisibility(
                                visible = state.urlError != null
                            ) {
                                state.urlError?.asString()?.let {
                                    Text(
                                        text = it,
                                        color = MaterialTheme.colorScheme.error,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(Spacing.xs))

                    Text(
                        text = UiText.StringResource(R.string.onboarding_landing_definitions_note)
                            .asString(),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                // endregion:   -- Form
                // region:      -- Actions
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(Spacing.s)
                    ) {
                        TextButton(
                            modifier = Modifier.weight(1f),
                            enabled = !state.fetching,
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
                            modifier = Modifier.weight(1f),
                            enabled = !state.fetching,
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
                    }

                    AnimatedContent(state.fetching) {
                        if (it) {
                            ContainedLoadingIndicator(
                                modifier = Modifier.fillMaxWidth(),
                            )
                        } else {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { onContinue(definitionsUrl, navNext) }
                            ) {
                                Text(
                                    text = UiText.StringResource(R.string.button_continue)
                                        .asString()
                                )
                            }
                        }
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
                    resetError = {},
                    onContinue = { _, _ -> },
                    navNext = {}
                )
            }
        }
    }
}