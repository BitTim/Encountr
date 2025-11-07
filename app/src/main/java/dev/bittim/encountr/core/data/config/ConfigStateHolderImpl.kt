/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ConfigStateHolderImpl.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.config

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import dev.bittim.encountr.core.data.user.repo.SaveRepository
import dev.bittim.encountr.core.di.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ConfigStateHolderImpl(
    private val dataStore: DataStore<Preferences>,
    private val saveRepository: SaveRepository,
) : ConfigStateHolder {
    @OptIn(ExperimentalUuidApi::class)
    private val _state = MutableStateFlow(ConfigState())

    override val rawState = _state.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)
    override val state = _state.filter {
        it.languageId != null && it.currentSaveUuid != null
    }.flatMapLatest { rawState ->
        Log.d("ConfigStateHolderImpl", "rawState: rawState = $rawState")
        saveRepository.get(rawState.currentSaveUuid!!).filterNotNull().map { save ->
            val resolvedConfigState = ResolvedConfigState(
                languageId = rawState.languageId!!,
                currentSave = save
            )

            Log.d("ConfigStateHolderImpl", "state: resolvedConfigState = $resolvedConfigState")
            resolvedConfigState
        }
    }.stateIn(
        scope = CoroutineScope(Dispatchers.IO),
        started = WhileSubscribed(5000),
        initialValue = null
    )

    // region:      -- Initializer

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun init() {
        Log.d("ConfigStateHolderImpl", "init")
        val config = dataStore.data.map { rawConfig ->
            Log.d("ConfigStateHolderImpl", "init: rawConfig = $rawConfig")
            ConfigState(
                isInitialized = true,
                languageId = rawConfig[Constants.DS_KEY_LANG_ID],
                currentSaveUuid = rawConfig[Constants.DS_KEY_CURR_SAVE_UUID]?.let { Uuid.parse(it) }
            )
        }.first()

        Log.d("ConfigStateHolderImpl", "init: config = $config")
        _state.update { config }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun reset() {
        _state.update { DEFAULT_CONFIG_STATE }
        dataStore.edit { config ->
            config[Constants.DS_KEY_LANG_ID] = DEFAULT_CONFIG_STATE.languageId!!
            config[Constants.DS_KEY_CURR_SAVE_UUID] =
                DEFAULT_CONFIG_STATE.currentSaveUuid!!.toString()
            Log.d("ConfigStateHolderImpl", "reset: config = $config")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getOnboardingCompleted(): StateFlow<Boolean?> {
        return _state.map {
            it.isInitialized && it.languageId != null && it.currentSaveUuid != null
        }.stateIn(
            scope = CoroutineScope(Dispatchers.IO),
            started = WhileSubscribed(5000),
            initialValue = null
        )
    }

    // endregion:   -- Initializer
    // region:      -- Setters

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun setLanguageId(id: Int) {
        _state.update { it.copy(languageId = id) }
        dataStore.edit { config ->
            config[Constants.DS_KEY_LANG_ID] = id
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun setCurrentSaveUuid(uuid: Uuid) {
        _state.update { it.copy(currentSaveUuid = uuid) }
        dataStore.edit { configState ->
            configState[Constants.DS_KEY_CURR_SAVE_UUID] = uuid.toString()
        }
    }

    // endregion:   -- Setters

    @OptIn(ExperimentalUuidApi::class)
    companion object {
        val DEFAULT_CONFIG_STATE = ConfigState(
            isInitialized = true,
            languageId = Constants.DEFAULT_LANG_ID,
            currentSaveUuid = Uuid.NIL
        )
    }
}