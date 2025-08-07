/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SaturationModifier.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.08.25, 02:32
 */

package dev.bittim.encountr.core.ui.util.extenstions.modifier

import android.graphics.RenderEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.asAndroidColorFilter
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer

const val SATURATION_DESATURATED = 0.3f

fun Modifier.saturation(saturation: Float): Modifier {
    return this.then(
        Modifier.graphicsLayer {
            renderEffect =
                RenderEffect.createColorFilterEffect(ColorFilter.colorMatrix(ColorMatrix().apply {
                    setToSaturation(saturation)
                }).asAndroidColorFilter()).asComposeRenderEffect()
        }
    )
}

fun Modifier.desaturate(): Modifier {
    return this.then(
        Modifier.saturation(SATURATION_DESATURATED)
    )
}