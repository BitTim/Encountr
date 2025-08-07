/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ColorUtil.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.08.25, 02:32
 */

package dev.bittim.encountr.core.ui.util.color

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun saturateColor(
    color: Color,
    saturation: Float
): Color {
    val alpha = color.alpha

    val hsv = FloatArray(3)
    android.graphics.Color.colorToHSV(
        color.toArgb(),
        hsv
    )
    hsv[1] *= saturation

    return Color.hsv(
        hsv[0],
        hsv[1],
        hsv[2],
        alpha
    )
}