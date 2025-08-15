/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GameSelector.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 20:59
 */

package dev.bittim.encountr.core.ui.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
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
    name: String,
    generation: String,
    image: Bitmap,
    onClick: () -> Unit = {},
    expanded: Boolean = false,
    iconSize: Dp = GameSelectorDefaults.iconSize,
    mainShape: CornerBasedShape = GameSelectorDefaults.mainShape,
    buttonShape: CornerBasedShape = GameSelectorDefaults.buttonShape
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(Spacing.xs)
    ) {
        Surface(
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = onClick),
            shape = mainShape,
            tonalElevation = Spacing.xxs
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.m),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GameIcon(
                    modifier = Modifier
                        .height(iconSize)
                        .aspectRatio(1f)
                        .clip(MaterialTheme.shapes.medium),
                    image = image
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = generation,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .clickable(onClick = onClick),
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
            )
        }
    }
}

@ComponentPreview
@Composable
fun GameSelectorPreview() {
    EncountrTheme {
        Surface {
            GameSelector(
                name = "Red",
                generation = "Generation I",
                image = BitmapFactory.decodeResource(
                    LocalContext.current.resources,
                    R.drawable.red
                )
            )
        }
    }
}