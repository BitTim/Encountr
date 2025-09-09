/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ConfigStateHolderImpl.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.09.25, 00:07
 */

package dev.bittim.encountr.core.data.config

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
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ConfigStateHolderImpl(
    private val dataStore: DataStore<Preferences>,
    private val saveRepository: SaveRepository
) : ConfigStateHolder {
    @OptIn(ExperimentalUuidApi::class)
    private val _state = MutableStateFlow(ConfigState())

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)
    override val state = _state.filter {
        it.definitionsUrl != null && it.languageName != null && it.currentSaveUuid != null
    }.flatMapLatest { rawState ->
        saveRepository.get(rawState.currentSaveUuid!!).filterNotNull().map { save ->
            ResolvedConfigState(
                definitionsUrl = rawState.definitionsUrl!!,
                languageName = rawState.languageName!!,
                currentSave = save
            )
        }
    }.stateIn(
        scope = CoroutineScope(Dispatchers.IO),
        started = WhileSubscribed(5000),
        initialValue = null
    )

    // region:      -- Initializer

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun init() {
        val config = dataStore.data.map { config ->
            ConfigState(
                finishedLoading = true,
                definitionsUrl = config[Constants.DS_KEY_DEFS_URL],
                languageName = config[Constants.DS_KEY_LANG_NAME],
                currentSaveUuid = config[Constants.DS_KEY_CURR_SAVE_UUID]?.let { Uuid.parse(it) }
            )
        }.firstOrNull()

        _state.update { config ?: ConfigState() }
    }

    override suspend fun reset() {
        _state.update { DEFAULT_CONFIG_STATE }
        dataStore.edit { config ->
            config[Constants.DS_KEY_DEFS_URL] = DEFAULT_CONFIG_STATE.definitionsUrl!!
            config[Constants.DS_KEY_LANG_NAME] = DEFAULT_CONFIG_STATE.languageName!!
        }
    }

    // endregion:   -- Initializer
    // region:      -- Setters

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun setDefinitionsUrl(url: String) {
        _state.update { it.copy(definitionsUrl = url) }
        dataStore.edit { config ->
            config[Constants.DS_KEY_DEFS_URL] = url
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun setLanguageName(name: String) {
        _state.update { it.copy(languageName = name) }
        dataStore.edit { config ->
            config[Constants.DS_KEY_LANG_NAME] = name
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
            finishedLoading = true,
            definitionsUrl = Constants.DEFAULT_DEFS_URL,
            languageName = Constants.DEFAULT_LANG_NAME,
            currentSaveUuid = Uuid.NIL
        )
    }
}