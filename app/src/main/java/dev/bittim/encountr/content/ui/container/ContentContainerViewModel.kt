/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ContentContainerViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   06.09.25, 00:23
 */

package dev.bittim.encountr.content.ui.container

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
        val configState = configStateHolder.configState.value
        val onboarded =
            configState.definitionsUrl != null && configState.languageName != null && configState.currentSaveUuid != null
        if (!onboarded) navToOnboarding()
    }
}