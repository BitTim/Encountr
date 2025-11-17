/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonListScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 02:31
 */

package dev.bittim.encountr.content.ui.screens.pokemon.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.bittim.encountr.R
import dev.bittim.encountr.core.ui.components.version.VersionIcon
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.UiText
import dev.bittim.encountr.core.ui.util.annotations.ScreenPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data object PokemonListScreenDefaults {
    val saveIconSize: Dp = 48.dp
    const val PLACEHOLDER_COUNT: Int = 10
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    state: PokemonListState,
    observePokedexName: (id: Int) -> Flow<String>,
    onPokedexChanged: (index: Int, searchQuery: String) -> Unit,
    applyFilter: (searchQuery: String) -> Unit,
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    val searchBarState = rememberSearchBarState()
    val textFieldState = rememberTextFieldState()
    var searchExpanded by rememberSaveable { mutableStateOf(false) }

    val searchQuery = textFieldState.text.toString()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = WindowInsets.safeContent.asPaddingValues().calculateTopPadding())
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.l),
            state = searchBarState,
            inputField = {
                SearchBarDefaults.InputField(
                    query = textFieldState.text.toString(),
                    onQueryChange = {
                        textFieldState.edit { replace(0, length, it) }
                        applyFilter(it)
                    },
                    onSearch = { searchExpanded = false },
                    expanded = searchExpanded,
                    enabled = state.pokedexIds.isNotEmpty(),
                    onExpandedChange = { searchExpanded = it },
                    placeholder = {
                        Text(
                            UiText.StringResource(R.string.search_pokemon).asString()
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            UiText.StringResource(R.string.search_pokemon).asString()
                        )
                    },
                    trailingIcon = {
                        Row {
                            IconButton(
                                onClick = {}
                            ) {
                                Icon(
                                    Icons.Default.FilterAlt,
                                    UiText.StringResource(R.string.filter_pokemon).asString()
                                )
                            }

                            Card(
                                shape = MaterialShapes.Cookie12Sided.toShape(),
                                onClick = {}
                            ) {
                                VersionIcon(
                                    modifier = Modifier
                                        .height(PokemonListScreenDefaults.saveIconSize)
                                        .aspectRatio(1f),
                                    imageUrl = state.version?.imageUrl
                                )
                            }
                        }
                    }
                )
            }
        )

        AnimatedVisibility(visible = state.pokedexIds.count() > 1) {
            PrimaryScrollableTabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Spacing.s)
            ) {
                state.pokedexIds.forEachIndexed { idx, pokedexId ->
                    val pokedexName by observePokedexName(pokedexId)
                        .collectAsStateWithLifecycle(null)

                    Tab(
                        selected = idx == selectedTab,
                        onClick = {
                            selectedTab = idx
                            onPokedexChanged(pokedexId, searchQuery) // TODO
                        },
                        text = { Text(text = pokedexName ?: "") }
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(Spacing.l),
            verticalArrangement = Arrangement.spacedBy(Spacing.s)
        ) {
//            items(state.filteredPokemon?.count() ?: PLACEHOLDER_COUNT) { idx ->
//                val pokemonCardState = if (
//                    state.filteredPokemon?.get(idx) == null ||
//                    state.pokedexes?.get(selectedTab) == null ||
//                    state.languageId == null ||
//                    state.version == null
//                ) {
//                    null
//                } else {
//                    PokemonCardState(
//                        pokemon = state.filteredPokemon[idx],
//                        pokedexId = state.pokedexes[selectedTab].id,
//                        language = state.languageName,
//                        version = state.version
//                    )
//                    null
//                }
//
//                PokemonCard(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .animateItem(),
//                    state = pokemonCardState
//                )
//            }
        }
    }
}

@ScreenPreview
@Composable
fun PokemonListScreenPreview() {
    EncountrTheme {
        Surface {
            PokemonListScreen(
                state = PokemonListState(),
                observePokedexName = { _ -> emptyFlow() },
                onPokedexChanged = { _, _ -> },
                applyFilter = {}
            )
        }
    }
}