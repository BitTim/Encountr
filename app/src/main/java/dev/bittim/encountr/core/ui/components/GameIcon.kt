/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GameIcon.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.08.25, 19:37
 */

package dev.bittim.encountr.core.ui.components

import android.graphics.Bitmap
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil3.imageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowHardware
import coil3.toBitmap
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.annotations.ComponentPreview
import dev.bittim.encountr.core.ui.util.color.ShaderGradient
import dev.bittim.encountr.core.ui.util.extenstions.modifier.pulseAnimation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun GameIcon(
    modifier: Modifier = Modifier,
    imageUrl: String?,
) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(imageUrl) {
        val imageLoader = context.imageLoader
        val request = ImageRequest.Builder(context)
            .data(imageUrl)
            .allowHardware(false)
            .build()

        val result = withContext(Dispatchers.IO) {
            imageLoader.execute(request)
        }

        if (result is SuccessResult) {
            val image = result.image
            bitmap = image.toBitmap()
        }
    }

    Crossfade(
        modifier = modifier,
        targetState = bitmap
    ) { bitmap ->
        if (bitmap != null) {
            val palette = Palette.from(bitmap).generate()

            ShaderGradientBackdrop(
                modifier = Modifier.fillMaxSize(),
                isDisabled = false,
                gradient = ShaderGradient.fromList(palette.swatches.take(4).map { Color(it.rgb) })
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
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pulseAnimation()
            )
        }
    }
}

@ComponentPreview
@Composable
fun GameIconPreview() {
    EncountrTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.spacedBy(Spacing.xs)
            ) {
                GameIcon(
                    modifier = Modifier
                        .width(56.dp)
                        .aspectRatio(1f)
                        .clip(MaterialTheme.shapes.medium),
                    imageUrl = null
                )

                GameIcon(
                    modifier = Modifier
                        .width(56.dp)
                        .aspectRatio(1f)
                        .clip(MaterialTheme.shapes.medium),
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/red-blue/transparent/6.png"
                )
            }
        }
    }
}