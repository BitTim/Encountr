/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GameSelector.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.08.25, 21:07
 */

package dev.bittim.encountr.core.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.bittim.encountr.R
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.UiText
import dev.bittim.encountr.core.ui.util.annotations.ComponentPreview

data object GameSelectorDefaults {
    val iconSize: Dp = 56.dp
    val mainShape = RoundedCornerShape(
        topStart = Spacing.m,
        topEnd = Spacing.xs,
        bottomEnd = Spacing.xs,
        bottomStart = Spacing.m
    )
    val buttonShape = RoundedCornerShape(
        topStart = Spacing.xs,
        topEnd = Spacing.m,
        bottomEnd = Spacing.m,
        bottomStart = Spacing.xs
    )
}

@Composable
fun GameSelector(
    modifier: Modifier = Modifier,
    state: GameCardState?,
    onClick: () -> Unit,
    expanded: Boolean = false,
    iconSize: Dp = GameSelectorDefaults.iconSize,
    mainShape: CornerBasedShape = GameSelectorDefaults.mainShape,
    buttonShape: CornerBasedShape = GameSelectorDefaults.buttonShape
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(Spacing.xs)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = onClick),
        ) {
            GameCard(
                state = state,
                iconSize = iconSize,
                shape = mainShape,
                elevation = Spacing.xxs
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .clickable(onClick = { if (state != null) onClick }),
            shape = buttonShape,
            tonalElevation = Spacing.xxs,
        ) {
            val rotation by animateFloatAsState(
                targetValue = if (expanded) -180f else 0f,
                label = "Arrow Rotation"
            )

            Icon(
                modifier = Modifier
                    .padding(horizontal = Spacing.xs)
                    .rotate(rotation),
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = UiText.StringResource(R.string.content_desc_gamesel_arrow_down)
                    .asString(),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@ComponentPreview
@Composable
fun GameSelectorPreview() {
    EncountrTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.spacedBy(Spacing.xs)
            ) {
                GameSelector(
                    state = null,
                    onClick = {}
                )

                GameSelector(
                    state = GameCardState(
                        name = "Red",
                        generation = "Generation I",
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/red-blue/transparent/6.png"
                    ),
                    onClick = {}
                )
            }
        }
    }
}