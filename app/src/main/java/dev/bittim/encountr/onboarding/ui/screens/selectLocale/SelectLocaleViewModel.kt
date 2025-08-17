/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SelectLocaleViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.08.25, 19:41
 */

package dev.bittim.encountr.onboarding.ui.screens.selectLocale

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectLocaleViewModel : ViewModel() {
    private val _state = MutableStateFlow(SelectLocaleState())
    val state = _state.asStateFlow()


}