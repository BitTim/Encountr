/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TeamListState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.09.25, 00:02
 */

package dev.bittim.encountr.content.ui.screens.teams.list

import dev.bittim.encountr.core.domain.model.user.Team

data class TeamListState(
    val teams: List<Team>? = null, // TODO: Replace Type
)
