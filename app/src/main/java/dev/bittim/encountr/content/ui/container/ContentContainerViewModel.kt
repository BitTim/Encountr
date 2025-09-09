/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ContentContainerViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.09.25, 00:07
 */

package dev.bittim.encountr.content.ui.container

import android.util.Log
import androidx.lifecycle.ViewModel
import dev.bittim.encountr.core.data.config.ConfigStateHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.uuid.ExperimentalUuidApi

class ContentContainerViewModel(
    private val configStateHolder: ConfigStateHolder,
) : ViewModel() {
    private var _state = MutableStateFlow(ContentContainerState())
    val state = _state.asStateFlow()

    @OptIn(ExperimentalUuidApi::class)
    fun checkOnboarded(
        navToOnboarding: () -> Unit,
    ) {
        val configState = configStateHolder.state.value
        Log.d("checkOnboarded", "configState: $configState")
        if (configState == null) navToOnboarding()
    }
}