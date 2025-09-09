/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonListScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   09.09.25, 03:54
 */

package dev.bittim.encountr.content.ui.screens.pokemon.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onFirstVisible
import dev.bittim.encountr.content.ui.components.PokemonCard
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.annotations.ScreenPreview

@Composable
fun PokemonListScreen(
    state: PokemonListState,
    fetchPokemon: (id: Int) -> Unit
) {
    val pokemon by derivedStateOf { state.pokemon }

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
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(Spacing.l),
            verticalArrangement = Arrangement.spacedBy(Spacing.s)
        ) {
            val entries = state.pokedexes?.get(selectedTab)?.pokemon

            items(entries?.count() ?: 0)
            {
                val entry = entries!![it]

                PokemonCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFirstVisible {
                            fetchPokemon(entry.pokemonSpecies.id)
                        },
                    data = pokemon[entry.pokemonSpecies.id]?.toPokemonCardData(entry.entryNumber)
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
                ),
                fetchPokemon = {}
            )
        }
    }
}