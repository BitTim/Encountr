/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonListScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.09.25, 00:53
 */

package dev.bittim.encountr.content.ui.screens.pokemon.list

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.bittim.encountr.content.ui.components.PokemonCard
import dev.bittim.encountr.content.ui.components.PokemonCardData
import dev.bittim.encountr.core.di.Constants
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.annotations.ScreenPreview

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PokemonListScreen(
    state: PokemonListState,
    onPokedexChanged: (index: Int) -> Unit,
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
                        onClick = {
                            selectedTab = idx
                            onPokedexChanged(pokedex.id)
                        },
                        text = {
                            Log.d(
                                "PokemonListScreen",
                                "Localized names available: ${pokedex.names}"
                            )
                            Log.d(
                                "PokemonListScreen", "Selected lang: ${
                                    (state.languageName
                                        ?: Constants.DEFAULT_LANG_NAME)
                                }"
                            )
                            Text(
                                text = pokedex.names.find {
                                    it.language.name == state.languageName
                                }?.name
                                    ?: pokedex.names.find { it.language.name == Constants.DEFAULT_LANG_NAME }?.name
                                    ?: pokedex.name
                            )
                        }
                    )
                }
            }
        }

        AnimatedVisibility(visible = state.pokemon == null) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ContainedLoadingIndicator()
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(Spacing.l),
            verticalArrangement = Arrangement.spacedBy(Spacing.s)
        ) {
            items(state.pokemon?.count() ?: 0)
            {
                val pokemon = state.pokemon?.get(it) ?: return@items
                val pokedexId = state.pokedexes?.get(selectedTab)?.id ?: return@items
                val languageName = state.languageName ?: return@items
                val version = state.version ?: return@items

                PokemonCard(
                    modifier = Modifier.fillMaxWidth(),
                    data = PokemonCardData(
                        pokemonOverview = pokemon,
                        pokedexId = pokedexId,
                        languageName = languageName,
                        version = version
                    )
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
                    pokedexes = listOf()
                ),
                onPokedexChanged = {}
            )
        }
    }
}