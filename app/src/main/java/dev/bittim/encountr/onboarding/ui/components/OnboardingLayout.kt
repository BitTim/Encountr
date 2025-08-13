/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       OnboardingLayout.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   14.08.25, 01:39
 */

package dev.bittim.encountr.onboarding.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import dev.bittim.encountr.core.ui.theme.Spacing

@Composable
fun OnboardingLayout(
    modifier: Modifier = Modifier,
    upper: @Composable (modifier: Modifier) -> Unit,
    lower: @Composable (modifier: Modifier) -> Unit,
    spacing: Dp = Spacing.xl
) {
    Column(
        modifier = modifier
    ) {
        upper(Modifier
            .weight(1f)
            .fillMaxWidth())
        Spacer(modifier = Modifier.height(spacing))
        lower(Modifier.fillMaxWidth())
    }
}