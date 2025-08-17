/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GameCard.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.08.25, 22:05
 */

package dev.bittim.encountr.core.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.annotations.ComponentPreview
import dev.bittim.encountr.core.ui.util.extenstions.modifier.pulseAnimation

data object GameCardDefaults {
    val iconSize: Dp = 56.dp
    val shape = RoundedCornerShape(Spacing.m)
    val elevation = Spacing.xxs
}

data class GameCardState(
    val name: String,
    val generation: String,
    val imageUrl: String?
)

@Composable
fun GameCard(
    modifier: Modifier = Modifier,
    state: GameCardState?,
    iconSize: Dp = GameCardDefaults.iconSize,
    shape: Shape = GameCardDefaults.shape,
    elevation: Dp = GameCardDefaults.elevation
) {
    val density = LocalDensity.current

    Surface(
        modifier = modifier,
        shape = shape,
        tonalElevation = elevation
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.m),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Crossfade(state?.imageUrl) {
                if (it != null) {
                    GameIcon(
                        modifier = Modifier
                            .height(iconSize)
                            .aspectRatio(1f)
                            .clip(MaterialTheme.shapes.medium),
                        imageUrl = it
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .height(iconSize)
                            .aspectRatio(1f)
                            .clip(MaterialTheme.shapes.medium)
                            .pulseAnimation()
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Crossfade(state?.generation) {
                    if (it != null) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .height(with(density) { MaterialTheme.typography.labelSmall.lineHeight.toDp() })
                                .clip(CircleShape)
                                .pulseAnimation()
                        )
                    }
                }

                Crossfade(state?.name) {
                    if (it != null) {
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
fun GameCardPreview() {
    EncountrTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.spacedBy(Spacing.xs)
            ) {
                GameCard(state = null)

                GameCard(
                    state = GameCardState(
                        name = "Red",
                        generation = "Generation I",
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/red-blue/transparent/6.png"
                    )
                )
            }
        }
    }
}