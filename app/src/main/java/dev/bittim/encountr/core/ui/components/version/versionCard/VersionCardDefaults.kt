/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionCardDefaults.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   09.11.25, 01:10
 */

package dev.bittim.encountr.core.ui.components.version.versionCard

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.bittim.encountr.core.ui.theme.Spacing

data object VersionCardDefaults {
    val iconSize: Dp = 256.dp
    val shape = RoundedCornerShape(Spacing.m)
    val elevation = Spacing.xxs
}