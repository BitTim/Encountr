/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LocationListState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.09.25, 01:07
 */

package dev.bittim.encountr.content.ui.screens.locations.list

import co.pokeapi.pokekotlin.model.Location

data class LocationListState(
    val locations: List<Location>? = null, // TODO: Replace Type
)
