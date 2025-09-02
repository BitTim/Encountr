/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LocaleCard.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   02.09.25, 04:48
 */

package dev.bittim.encountr.core.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.bittim.encountr.R
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.UiText
import dev.bittim.encountr.core.ui.util.annotations.ComponentPreview
import dev.bittim.encountr.core.ui.util.extenstions.modifier.pulseAnimation

data object LocaleCardDefaults {
    val height = 64.dp
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
    isSelected: Boolean,
    onClick: () -> Unit,
    iconSize: Dp = LocaleCardDefaults.height,
    iconElevation: Dp = LocaleCardDefaults.iconElevation
) {
    val density = LocalDensity.current
    val outlineColor =
        animateColorAsState(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)

    OutlinedCard(
        modifier = modifier.clickable(onClick = onClick),
        border = BorderStroke(1.dp, outlineColor.value)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.m),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Crossfade(
                targetState = state?.countryCode
            ) {
                if (!it.isNullOrEmpty()) {
                    Surface(
                        modifier = Modifier
                            .size(iconSize)
                            .aspectRatio(1f)
                            .clip(MaterialTheme.shapes.medium),
                        tonalElevation = iconElevation
                    ) {
                        FlagIcon(
                            modifier = Modifier.padding(Spacing.s),
                            countryCode = it
                        )
                    }
                } else {
                    Box(modifier = Modifier
                        .size(iconSize)
                        .aspectRatio(1f)
                        .clip(MaterialTheme.shapes.medium)
                        .pulseAnimation())
                }
            }

            Crossfade(
                modifier = Modifier.weight(1f),
                targetState = state?.name
            ) {
                if (!it.isNullOrEmpty()) {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = Spacing.l)
                            .height(with(density) { MaterialTheme.typography.bodyLarge.lineHeight.toDp() })
                            .clip(CircleShape)
                            .pulseAnimation()
                    )
                }
            }

            AnimatedContent(
                modifier = Modifier.padding(end = Spacing.m),
                targetState = isSelected,
            ) {
                if (it) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = UiText.StringResource(R.string.content_desc_selected)
                            .asString(),
                        tint = MaterialTheme.colorScheme.primary
                    )
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
                LocaleCard(state = null, isSelected = false, onClick = {})
                LocaleCard(
                    state = LocaleCardState(name = "Test", countryCode = "de"),
                    isSelected = false,
                    onClick = {})
                LocaleCard(
                    state = LocaleCardState(name = "Test", countryCode = "de"),
                    isSelected = true,
                    onClick = {})
            }
        }
    }
}