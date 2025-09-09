/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonCard.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   09.09.25, 03:32
 */

package dev.bittim.encountr.content.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CatchingPokemon
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.annotations.ComponentPreview
import dev.bittim.encountr.core.ui.util.extenstions.modifier.pulseAnimation
import dev.bittim.encountr.core.ui.util.font.getScaledLineHeightFromFontStyle

data object PokemonCardDefaults {
    val elevation = Spacing.xxs
    val iconWidth = 96.dp
}

data class PokemonCardData(
    val id: Int,
    val entryNumber: Int,
    val name: String,
    val height: String,
    val weight: String,
    val imageUrl: String,
    val types: List<String>,
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    data: PokemonCardData?,
    iconWidth: Dp = PokemonCardDefaults.iconWidth,
    elevation: Dp = PokemonCardDefaults.elevation
) {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current

    Surface(
        modifier = modifier.clip(MaterialTheme.shapes.medium),
        tonalElevation = elevation,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.s),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surfaceContainerHighest)
            ) {
                Crossfade(
                    targetState = data,
                ) {
                    if (it != null) {
                        AsyncImage(
                            modifier = Modifier
                                .width(iconWidth)
                                .aspectRatio(1f),
                            model = it.imageUrl,
                            contentDescription = it.name,
                            contentScale = ContentScale.Fit,
                            filterQuality = FilterQuality.None
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .width(iconWidth)
                                .aspectRatio(1f)
                                .clip(MaterialTheme.shapes.medium)
                                .pulseAnimation()
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Crossfade(
                    targetState = data,
                ) {
                    if (it != null) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = it.height,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Text(
                                text = "•",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Text(
                                text = it.weight,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(
                                    getScaledLineHeightFromFontStyle(
                                        density,
                                        configuration,
                                        MaterialTheme.typography.labelMedium
                                    )
                                )
                                .clip(CircleShape)
                                .pulseAnimation()
                        )
                    }
                }

                Crossfade(
                    targetState = data,
                ) {
                    if (it != null) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(Spacing.s),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = it.name,
                                style = MaterialTheme.typography.titleLarge,
                            )

                            Text(
                                text = "#${it.entryNumber}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(
                                    getScaledLineHeightFromFontStyle(
                                        density,
                                        configuration,
                                        MaterialTheme.typography.titleLarge
                                    )
                                )
                                .clip(CircleShape)
                                .pulseAnimation()
                        )
                    }
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.xs)
                ) {
                    items(data?.types ?: emptyList()) { type ->
                        Text(text = type)
                    }
                }
            }

            OutlinedIconToggleButton(
                modifier = Modifier.padding(end = Spacing.m),
                onCheckedChange = { /*TODO*/ },
                checked = false,
            ) {
                Icon(Icons.Default.CatchingPokemon, contentDescription = null)
            }
        }
    }
}

@ComponentPreview
@Composable
fun PokemonCardPreview() {
    EncountrTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.spacedBy(Spacing.s),
            ) {
                PokemonCard(
                    data = PokemonCardData(
                        id = 1,
                        entryNumber = 1,
                        name = "Bulbasaur",
                        height = "0.7 m",
                        weight = "6.9 kg",
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
                        types = listOf("Grass", "Poison"),
                    )
                )

                PokemonCard(data = null)
            }
        }
    }
}