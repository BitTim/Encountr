/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       OnboardingActions.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   14.08.25, 03:11
 */

package dev.bittim.encountr.onboarding.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.bittim.encountr.R
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.util.UiText
import dev.bittim.encountr.core.ui.util.annotations.ComponentPreview

@Composable
fun OnboardingActions(
    modifier: Modifier = Modifier,
    onContinue: () -> Unit,
    onBack: () -> Unit,
    continueEnabled: Boolean = true
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedButton(
            onClick = onBack
        ) {
            Text(text = UiText.StringResource(R.string.button_back).asString())
        }

        Button(
            onClick = onContinue,
            enabled = continueEnabled
        ) {
            Text(text = UiText.StringResource(R.string.button_continue).asString())
        }
    }
}

@ComponentPreview
@Composable
fun OnboardingActionsPreview() {
    EncountrTheme {
        Surface {
            OnboardingActions(
                modifier = Modifier.fillMaxWidth(),
                onContinue = {},
                onBack = {}
            )
        }
    }
}