/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       OnboardingScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   08.08.25, 16:50
 */

package dev.bittim.encountr.onboarding.ui

import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import dev.bittim.encountr.R
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.UiText
import dev.bittim.encountr.core.ui.util.annotations.ScreenPreview

@Composable
fun OnboardingScreen() {
    val context = LocalContext.current

    var definitionsUrl by rememberSaveable { mutableStateOf("https://bittim.github.io/Encountr/definitions.json") }
    var isEditing by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        contentWindowInsets = WindowInsets.safeContent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(Spacing.xl)
        ) {
            // region:      -- Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier
                        .width(56.dp)
                        .aspectRatio(1f),
                    model = R.drawable.ic_launcher,
                    contentDescription = UiText.StringResource(R.string.app_name).asString(),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = UiText.StringResource(R.string.app_name).asString(),
                    style = MaterialTheme.typography.displayMedium
                )
            }
            // endregion:   -- Header
            // region:      -- Description
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Spacing.s)
            ) {
                Text(
                    text = UiText.StringResource(R.string.onboarding_title).asString(),
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = UiText.StringResource(R.string.onboarding_description).asString(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            // endregion:   -- Description
            // region:      -- Form
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Spacing.s)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        modifier = Modifier.weight(1f),
                        value = definitionsUrl,
                        readOnly = !isEditing,
                        singleLine = true,
                        onValueChange = {
                            definitionsUrl = it
                        },
                        label = {
                            Text(
                                text = UiText.StringResource(R.string.text_field_definitions_url)
                                    .asString()
                            )
                        }
                    )

                    AnimatedContent(
                        targetState = isEditing,
                    ) {
                        if (it) {
                            Row {
                                IconButton(
                                    onClick = { }
                                ) {
                                    Icon(
                                        Icons.Default.RestartAlt,
                                        UiText.StringResource(R.string.content_desc_reset)
                                            .asString()
                                    )
                                }

                                FilledTonalIconButton(
                                    onClick = {
                                        // TODO: Validation
                                        isEditing = false
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Check,
                                        UiText.StringResource(R.string.content_desc_confirm)
                                            .asString()
                                    )
                                }
                            }
                        } else {
                            IconButton(
                                onClick = { isEditing = true }
                            ) {
                                Icon(
                                    Icons.Default.Edit,
                                    UiText.StringResource(R.string.content_desc_edit).asString()
                                )
                            }
                        }
                    }
                }

                Text(
                    text = UiText.StringResource(R.string.onboarding_definitions_note).asString(),
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
                        onClick = { }
                    ) {
                        Icon(
                            Icons.Default.PrivacyTip,
                            UiText.StringResource(R.string.content_desc_privacy_policy).asString()
                        )
                        Spacer(modifier = Modifier.width(Spacing.s))
                        Text(
                            text = UiText.StringResource(R.string.button_privacy_policy).asString()
                        )
                    }

                    TextButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                context.resources.getString(R.string.url_github).toUri()
                            )
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

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { }
                ) {
                    Text(text = UiText.StringResource(R.string.button_continue).asString())
                }
            }
            // endregion:   -- Actions
        }
    }
}

@ScreenPreview
@Composable
fun OnboardingScreenPreview() {
    EncountrTheme {
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Spacing.m),
            ) {
                OnboardingScreen()
            }
        }
    }
}