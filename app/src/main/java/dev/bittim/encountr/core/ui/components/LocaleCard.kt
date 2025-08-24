/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LocaleCard.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   24.08.25, 19:50
 */

package dev.bittim.encountr.core.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.annotations.ComponentPreview
import dev.bittim.encountr.core.ui.util.extenstions.modifier.pulseAnimation

data object LocaleCardDefaults {
    val height = 56.dp
    val elevation: Dp = Spacing.xxs
    val iconElevation: Dp = Spacing.xs
}

data class LocaleCardState(
    val name: String,
    val countryCode: String,
)

@Composable
fun LocaleCard(
    modifier: Modifier = Modifier,
    state: LocaleCardState?,
    iconSize: Dp = LocaleCardDefaults.height,
    elevation: Dp = LocaleCardDefaults.elevation,
    iconElevation: Dp = LocaleCardDefaults.iconElevation
) {
    val density = LocalDensity.current

    Surface(
        modifier = modifier,
        tonalElevation = elevation
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.xs)
        ) {
            Crossfade(
                targetState = state?.countryCode
            ) {
                if (!it.isNullOrEmpty()) {
                    Surface(
                        modifier = Modifier
                            .size(iconSize)
                            .aspectRatio(1f),
                        tonalElevation = iconElevation
                    ) {
                        FlagIcon(countryCode = it)
                    }
                } else {
                    Box(modifier = Modifier
                        .size(iconSize)
                        .aspectRatio(1f)
                        .pulseAnimation())
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(Spacing.xs)
            ) {
                Crossfade(targetState = state?.name) {
                    if (!it.isNullOrEmpty()) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .height(with(density) { MaterialTheme.typography.bodyLarge.lineHeight.toDp() })
                                .pulseAnimation()
                                .clip(CircleShape)
                        )
                    }
                }
            }
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
                LocaleCard(state = LocaleCardState(name = "Test", countryCode = "de"))
            }
        }
    }
}