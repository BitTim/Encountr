/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonListScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   08.09.25, 02:52
 */

package dev.bittim.encountr.content.ui.screens.pokemon.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.bittim.encountr.content.ui.components.PokemonCard
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.annotations.ScreenPreview

@Composable
fun PokemonListScreen(
    state: PokemonListState,
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = Spacing.xxxl)
    ) {
        AnimatedVisibility(
            visible = state.pokedexes != null && state.pokedexes.count() > 1
        ) {
            PrimaryScrollableTabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.fillMaxWidth()
            ) {
                state.pokedexes!!.forEachIndexed { idx, pokedex ->
                    Tab(
                        selected = idx == selectedTab,
                        onClick = { selectedTab = idx },
                        text = { Text(pokedex.name) }
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = Spacing.l,
                    top = Spacing.l,
                    end = Spacing.l
                )
        ) {
            val pokemon = state.pokedexes?.get(selectedTab)?.pokemon

            items(pokemon?.count() ?: 0)
            {
                PokemonCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.s),
                    data = pokemon?.get(it) ?: return@items
                )
            }
        }
    }
}

@ScreenPreview
@Composable
fun PokemonListScreenPreview() {
    EncountrTheme {
        Surface {
            PokemonListScreen(
                state = PokemonListState(
                    pokedexes = listOf(
                        Pokedex(1, "Test", emptyList()),
                        Pokedex(2, "Test2", emptyList())
                    )
                )
            )
        }
    }
}