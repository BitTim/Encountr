/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       FontUtil.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   03.09.25, 04:09
 */

package dev.bittim.encountr.core.ui.util.font

import android.content.res.Configuration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

fun getScaledLineHeightFromFontStyle(
    density: Density,
    configuration: Configuration,
    textStyle: TextStyle,
): Dp {
    return with(density) {
        textStyle.lineHeight.toDp() * configuration.fontScale
    }
}