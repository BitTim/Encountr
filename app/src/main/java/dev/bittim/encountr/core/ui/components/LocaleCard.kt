/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LocaleCard.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.08.25, 19:41
 */

package dev.bittim.encountr.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.annotations.ComponentPreview

data object LocaleCardDefaults {
    val iconSize = 56.dp
    val elevation: Dp = Spacing.xxs
}

data class LocaleCardState(
    val name: String,
)

@Composable
fun LocaleCard(
    modifier: Modifier = Modifier,
    state: LocaleCardState?,
    iconSize: Dp = LocaleCardDefaults.iconSize,
    elevation: Dp = LocaleCardDefaults.elevation
) {
    Surface(
        modifier = modifier,
        tonalElevation = elevation
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.xs)
        ) {

        }
    }
}

@ComponentPreview
@Composable
fun LocaleCardPreview() {
    EncountrTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.spacedBy(Spacing.xs)
            ) {
                LocaleCard(state = null)
                LocaleCard(state = LocaleCardState(name = "Test"))
            }
        }
    }
}