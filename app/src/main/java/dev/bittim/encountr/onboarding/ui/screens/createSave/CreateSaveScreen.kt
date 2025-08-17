/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       CreateSaveScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.08.25, 22:05
 */

package dev.bittim.encountr.onboarding.ui.screens.createSave

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import dev.bittim.encountr.core.ui.components.GameCard
import dev.bittim.encountr.core.ui.components.GameSelector
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.annotations.ScreenPreview
import dev.bittim.encountr.onboarding.ui.components.OnboardingActions
import dev.bittim.encountr.onboarding.ui.components.OnboardingLayout
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CreateSaveScreen(
    state: CreateSaveState,
    loadNext: () -> Unit,
    navBack: () -> Unit,
    navNext: () -> Unit,
) {
    var selectedGameIndex by rememberSaveable { mutableIntStateOf(0) }
    val selectedGameObject =
        remember(state.games, selectedGameIndex) { state.games?.get(selectedGameIndex) }

    val lazyListState = rememberLazyListState()
    var selectorExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(state.games) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .distinctUntilChanged()
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex == state.games?.lastIndex) {
                    loadNext()
                }
            }
    }

    OnboardingLayout(
        modifier = Modifier.fillMaxSize(),
        upper = {
            Box(modifier = it) { }
        },
        lower = {
            Column(
                modifier = it
            ) {
                GameSelector(
                    modifier = Modifier.fillMaxWidth(),
                    state = selectedGameObject?.toGameSelectorState(),
                    expanded = selectorExpanded,
                    onClick = { selectorExpanded = true }
                )


                Spacer(modifier = Modifier.height(Spacing.xxl))

                OnboardingActions(
                    modifier = Modifier.fillMaxWidth(),
                    onContinue = navNext,
                    onBack = navBack
                )
            }
        }
    )

    if (selectorExpanded) {
        Dialog(
            onDismissRequest = { selectorExpanded = false },
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().padding(Spacing.m),
                    verticalArrangement = Arrangement.spacedBy(Spacing.s),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    state = lazyListState
                ) {
                    items(state.games?.count() ?: 10) { idx ->
                        GameCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedGameIndex = idx
                                    selectorExpanded = false
                                },
                            state = state.games?.getOrNull(idx)?.toGameSelectorState(),
                            elevation = Spacing.none,
                        )
                    }

                    if (state.error != null) {
                        item {
                            Text(
                                text = state.error,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                    if (state.isLoading) {
                        item { ContainedLoadingIndicator() }
                    }
                }
            }
        }
    }
}

@ScreenPreview
@Composable
fun CreateSaveScreenPreview() {
    EncountrTheme {
        Surface {
            CreateSaveScreen(
                state = CreateSaveState(),
                loadNext = {},
                navBack = {},
                navNext = {}
            )
        }
    }
}