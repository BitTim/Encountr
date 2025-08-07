/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GameIcon.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.08.25, 17:02
 */

package dev.bittim.encountr.core.ui.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import dev.bittim.encountr.R
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.annotations.ComponentPreview
import dev.bittim.encountr.core.ui.util.color.ShaderGradient

@Composable
fun GameIcon(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.red)
    val palette = Palette.from(bitmap).generate()

    ShaderGradientBackdrop(
        modifier = modifier,
        false,
        ShaderGradient.fromList(palette.swatches.take(4).map { Color(it.rgb) })
    ) {
        Box(
            modifier = Modifier.padding(Spacing.xs)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                filterQuality = FilterQuality.None
            )
        }
    }
}

@ComponentPreview
@Composable
fun GameIconPreview() {
    EncountrTheme {
        Surface {
            GameIcon(
                modifier = Modifier
                    .width(56.dp)
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.medium)
            )
        }
    }
}