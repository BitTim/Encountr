/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonCard.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   19.09.25, 19:49
 */

package dev.bittim.encountr.content.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CatchingPokemon
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButtonShapes
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
import dev.bittim.encountr.core.data.pokeapi.mapping.mapPokemonSprite
import dev.bittim.encountr.core.data.pokeapi.mapping.mapTypeSprite
import dev.bittim.encountr.core.domain.model.pokeapi.MappedPokemonSprites
import dev.bittim.encountr.core.domain.model.pokeapi.PokemonOverview
import dev.bittim.encountr.core.domain.model.pokeapi.Type
import dev.bittim.encountr.core.domain.model.pokeapi.Version
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.annotations.ComponentPreview
import dev.bittim.encountr.core.ui.util.extenstions.modifier.pulseAnimation
import dev.bittim.encountr.core.ui.util.font.getScaledLineHeightFromFontStyle
import kotlinx.coroutines.runBlocking

data object PokemonCardDefaults {
    val elevation = Spacing.xxs
    val iconWidth = 96.dp
}

data class PokemonCardState(
    val id: Int,
    val entryNumber: Int,
    val name: String,
    val height: String,
    val weight: String,
    val sprites: MappedPokemonSprites,
    val types: List<PokemonCardTypeState>,
) {
    constructor(
        pokemonOverview: PokemonOverview,
        pokedexId: Int,
        languageName: String,
        version: Version
    ) : this(
        id = pokemonOverview.id,
        entryNumber = pokemonOverview.entryNumbers.find { it.pokedexId == pokedexId }?.entryNumber
            ?: -1,
        name = pokemonOverview.localizedNames.find { it.languageName == languageName }?.value
            ?: pokemonOverview.name,
        height = pokemonOverview.height,
        weight = pokemonOverview.weight,
        sprites = runBlocking { mapPokemonSprite(pokemonOverview.sprites, version) },
        types = pokemonOverview.types.map {
            PokemonCardTypeState(it, languageName, version)
        }
    )
}

data class PokemonCardTypeState(
    val name: String,
    val imageUrl: String?
) {
    constructor(
        type: Type,
        languageName: String,
        version: Version
    ) : this(
        name = type.localizedNames.find { it.languageName == languageName }?.value ?: type.name,
        imageUrl = runBlocking { mapTypeSprite(type.sprites, version) }
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    state: PokemonCardState?,
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
                    targetState = state,
                ) {
                    if (it != null) {
                        AsyncImage(
                            modifier = Modifier
                                .width(iconWidth)
                                .aspectRatio(1f),
                            model = it.sprites.frontDefault,
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
                    targetState = state,
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
                    targetState = state,
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

                Spacer(modifier = Modifier.height(Spacing.xs))

                Crossfade(
                    targetState = state,
                ) {
                    if (it != null) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(Spacing.xs)
                        ) {
                            items(it.types.count()) { index ->
                                val type = it.types[index]
                                if (type.imageUrl != null) {
                                    AsyncImage(
                                        modifier = Modifier.height(
                                            getScaledLineHeightFromFontStyle(
                                                density,
                                                configuration,
                                                MaterialTheme.typography.labelMedium
                                            )
                                        ),
                                        model = type.imageUrl,
                                        contentDescription = type.name,
                                        contentScale = ContentScale.FillHeight,
                                        filterQuality = FilterQuality.None
                                    )
                                } else {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
                                    ) {
                                        Text(
                                            text = type.name,
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        )

                                        if (it.types.count() > 1 && index < it.types.count() - 1) {
                                            Text(
                                                text = "•",
                                                style = MaterialTheme.typography.labelMedium,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(
                                    getScaledLineHeightFromFontStyle(
                                        density,
                                        configuration,
                                        MaterialTheme.typography.labelSmall
                                    )
                                )
                                .clip(CircleShape)
                                .pulseAnimation()
                        )
                    }
                }
            }

            OutlinedIconToggleButton(
                modifier = Modifier
                    .padding(end = Spacing.m)
                    .size(
                        IconButtonDefaults.smallContainerSize(
                            IconButtonDefaults.IconButtonWidthOption.Wide
                        )
                    ),
                onCheckedChange = { /*TODO*/ },
                checked = false,
                shapes = IconToggleButtonShapes(
                    shape = IconButtonDefaults.smallRoundShape,
                    pressedShape = IconButtonDefaults.smallPressedShape,
                    checkedShape = IconButtonDefaults.smallSelectedRoundShape
                ),
                enabled = state != null,
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
                    state = PokemonCardState(
                        id = 1,
                        entryNumber = 1,
                        name = "Bulbasaur",
                        height = "0.7 m",
                        weight = "6.9 kg",
                        sprites = MappedPokemonSprites(
                            frontDefault = null,
                            frontShiny = null,
                            backDefault = null,
                            backShiny = null,
                            frontFemale = null,
                            frontShinyFemale = null,
                            backFemale = null,
                            backShinyFemale = null,
                        ),
                        types = listOf(
                            PokemonCardTypeState("Grass", ""),
                            PokemonCardTypeState("Poison", ""),
                        )
                    )
                )

                PokemonCard(state = null)
            }
        }
    }
}