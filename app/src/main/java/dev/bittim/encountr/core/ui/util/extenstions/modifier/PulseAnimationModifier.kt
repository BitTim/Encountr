/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PulseAnimationModifier.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.08.25, 02:32
 */

package dev.bittim.encountr.core.ui.util.extenstions.modifier

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind

fun Modifier.pulseAnimation(): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "Loading pulse")
    val color by transition.animateColor(
        initialValue = MaterialTheme.colorScheme.surfaceContainerLow,
        targetValue = MaterialTheme.colorScheme.surfaceContainerHigh,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse,
        ), label = "Pulse animation"
    )

    drawBehind { drawRect(color = color) }
}