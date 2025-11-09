/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ShaderGradientBackdrop.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   09.11.25, 01:10
 */

package dev.bittim.encountr.core.ui.components.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.bittim.encountr.core.ui.util.color.ShaderGradient
import dev.bittim.encountr.core.ui.util.color.drawShaderGradient
import dev.bittim.encountr.core.ui.util.extenstions.modifier.conditional

@Composable
fun ShaderGradientBackdrop(
    modifier: Modifier = Modifier,
    isDisabled: Boolean,
    gradient: ShaderGradient?,
    content: @Composable (Boolean) -> Unit,
) {
    Box(
        modifier = modifier
            .background(Color.Transparent)
            .conditional(gradient != null) {
                drawShaderGradient(gradient!!, isDisabled)
            }
    ) {
        content(gradient != null)
    }
}