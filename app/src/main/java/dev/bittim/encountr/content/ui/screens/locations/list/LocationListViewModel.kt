/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LocationListViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.09.25, 01:07
 */

package dev.bittim.encountr.content.ui.screens.locations.list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LocationListViewModel : ViewModel() {
    private val _state = MutableStateFlow(LocationListState())
    val state = _state.asStateFlow()
}