/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TeamListScreen.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.09.25, 01:07
 */

package dev.bittim.encountr.content.ui.screens.teams.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.bittim.encountr.core.ui.theme.EncountrTheme
import dev.bittim.encountr.core.ui.theme.Spacing
import dev.bittim.encountr.core.ui.util.annotations.ScreenPreview


@Composable
fun TeamListScreen(
    state: TeamListState,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(5)
        {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Spacing.s)
            ) {
                Column(
                    modifier = Modifier.padding(Spacing.s)
                ) {
                    Text(
                        text = "Team $it",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = "Placeholder",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@ScreenPreview
@Composable
fun LocationListScreenPreview() {
    EncountrTheme {
        Surface {
            TeamListScreen(
                state = TeamListState()
            )
        }
    }
}