/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonListScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   04.09.25, 23:26
 */

package dev.bittim.encountr.content.ui.screens.pokemon.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.util.annotations.ScreenPreview

@Composable
fun PokemonListScreen(
    state: PokemonListState,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(100)
        {
            Text(
                modifier = Modifier.clickable(onClick = {}),
                text = "Pokemon #$it"
            )
        }
    }
}

@ScreenPreview
@Composable
fun PokemonListScreenPreview() {
    EncountrTheme {
        Surface {
            PokemonListScreen(
                state = PokemonListState()
            )
        }
    }
}