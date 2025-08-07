/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       OnboardingScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.08.25, 02:32
 */

package dev.bittim.encountr.onboarding.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.bittim.encountr.R
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.UiText
import dev.bittim.encountr.core.ui.util.annotations.ScreenPreview

@Composable
fun OnboardingScreen() {
    var saveName by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
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
                model = R.drawable.logo,
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
        Text(
            text = UiText.StringResource(R.string.onboarding_description).asString(),
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = UiText.StringResource(R.string.onboarding_note).asString(),
            style = MaterialTheme.typography.labelMedium
        )
        // endregion:   -- Description
        // region:      -- Form
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.s)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = saveName,
                onValueChange = {
                    saveName = it
                },
                label = { Text(text = "Name") }
            )
        }
        // endregion:   -- Form
        // region:      -- Actions
        // endregion:   -- Actions
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