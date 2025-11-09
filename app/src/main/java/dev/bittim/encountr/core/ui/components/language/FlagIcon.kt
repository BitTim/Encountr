/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       FlagIcon.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   09.11.25, 01:10
 */

package dev.bittim.encountr.core.ui.components.language

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage

data object FlagIconDefaults {
    const val BASE_URL: String = "https://flagcdn.com/64x48/"
}

@Composable
fun FlagIcon(
    modifier: Modifier = Modifier,
    countryCode: String,
) {
    AsyncImage(
        modifier = modifier,
        model = "${FlagIconDefaults.BASE_URL}${countryCode.lowercase()}.png",
        contentDescription = countryCode,
        contentScale = ContentScale.Fit
    )
}