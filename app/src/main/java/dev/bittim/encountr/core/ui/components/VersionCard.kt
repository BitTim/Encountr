/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionCard.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   19.09.25, 18:24
 */

package dev.bittim.encountr.core.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.bittim.encountr.core.di.Constants
import dev.bittim.encountr.core.domain.model.pokeapi.Version
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.annotations.ComponentPreview
import dev.bittim.encountr.core.ui.util.extenstions.modifier.pulseAnimation
import dev.bittim.encountr.core.ui.util.font.getScaledLineHeightFromFontStyle

data object GameCardDefaults {
    val iconSize: Dp = 256.dp
    val shape = RoundedCornerShape(Spacing.m)
    val elevation = Spacing.xxs
}

data class VersionCardState(
    val name: String,
    val generation: String,
    val imageUrl: String?
) {
    constructor(version: Version, languageName: String) : this(
        name = version.localizedNames.find { it.languageName == languageName }?.value
            ?: version.localizedNames.find { it.languageName == Constants.DEFAULT_LANG_NAME }?.value
            ?: version.name,
        generation = version.localizedGenerationNames.find { it.languageName == languageName }?.value
            ?: version.localizedGenerationNames.find { it.languageName == Constants.DEFAULT_LANG_NAME }?.value
            ?: version.generationName,
        imageUrl = version.imageUrl
    )
}

@Composable
fun VersionCard(
    modifier: Modifier = Modifier,
    state: VersionCardState?,
    iconSize: Dp = GameCardDefaults.iconSize,
    shape: Shape = GameCardDefaults.shape,
    elevation: Dp = GameCardDefaults.elevation
) {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current

    Surface(
        modifier = modifier,
        shape = shape,
        tonalElevation = elevation
    ) {
        Column {
            Crossfade(state?.imageUrl) {
                if (it != null) {
                    VersionIcon(
                        modifier = Modifier
                            .width(iconSize)
                            .aspectRatio(1f)
                            .clip(MaterialTheme.shapes.medium),
                        imageUrl = it
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .width(iconSize)
                            .aspectRatio(1f)
                            .clip(MaterialTheme.shapes.medium)
                            .pulseAnimation()
                    )
                }
            }

            Column(
                modifier = Modifier
                    .width(iconSize)
                    .padding(Spacing.m)
            ) {
                Crossfade(state?.generation) {
                    if (it != null) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(
                                    getScaledLineHeightFromFontStyle(
                                        density, configuration, MaterialTheme.typography.labelSmall
                                    ) - 1.dp
                                )
                                .padding(bottom = 1.dp)
                                .clip(CircleShape)
                                .pulseAnimation()
                        )
                    }
                }

                Crossfade(state?.name) {
                    if (it != null) {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(
                                    getScaledLineHeightFromFontStyle(
                                        density, configuration, MaterialTheme.typography.bodyLarge
                                    ) - 1.dp
                                )
                                .padding(top = 1.dp)
                                .clip(CircleShape)
                                .pulseAnimation()
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
                VersionCard(state = null)

                VersionCard(
                    state = VersionCardState(
                        name = "Red",
                        generation = "Generation I",
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/red-blue/transparent/6.png"
                    )
                )
            }
        }
    }
}