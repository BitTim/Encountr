/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonCard.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   08.09.25, 01:18
 */

package dev.bittim.encountr.content.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import dev.bittim.encountr.content.ui.screens.pokemon.list.Pokemon
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.annotations.ComponentPreview

data object PokemonCardDefaults {
    val elevation = Spacing.xxs
    val iconWidth = 96.dp
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    data: Pokemon, // TODO: Replace with proper types
    iconWidth: Dp = PokemonCardDefaults.iconWidth,
    elevation: Dp = PokemonCardDefaults.elevation
) {
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
                AsyncImage(
                    modifier = Modifier
                        .width(iconWidth)
                        .aspectRatio(1f),
                    model = data.imageUrl,
                    contentDescription = data.name,
                    contentScale = ContentScale.Fit,
                    filterQuality = FilterQuality.None
                )
            }

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = data.height,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "•",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = data.weight,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.s),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = data.name,
                        style = MaterialTheme.typography.titleLarge,
                    )

                    Text(
                        text = "#${data.entryNumber}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.xs)
                ) {
                    items(data.types) { type ->
                        Text(text = type)
                    }
                }
            }

            OutlinedIconToggleButton(
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
            PokemonCard(
                data = Pokemon(
                    id = 1,
                    entryNumber = 1,
                    name = "Bulbasaur",
                    height = "0.7 m",
                    weight = "6.9 kg",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
                    types = listOf("Grass", "Poison"),
                )
            )
        }
    }
}