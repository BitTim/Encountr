/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ConfigStateHolderImpl.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   04.09.25, 18:06
 */

package dev.bittim.encountr.core.data.config

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import dev.bittim.encountr.core.di.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class ConfigStateHolderImpl(
    private val dataStore: DataStore<Preferences>
) : ConfigStateHolder {
    private val _configState = MutableStateFlow(ConfigState())
    override val configState = _configState

    // region:      -- Initializer

    override suspend fun init() {
        val config = dataStore.data.map { config ->
            ConfigState(
                definitionsUrl = config[Constants.DS_KEY_DEFS_URL],
                languageName = config[Constants.DS_KEY_LANG_NAME],
            )
        }.firstOrNull()

        _configState.update { config ?: ConfigState() }
    }

    override suspend fun reset() {
        _configState.update { DEFAULT_CONFIG_STATE }
        dataStore.edit { config ->
            config[Constants.DS_KEY_DEFS_URL] = DEFAULT_CONFIG_STATE.definitionsUrl!!
            config[Constants.DS_KEY_LANG_NAME] = DEFAULT_CONFIG_STATE.languageName!!
        }
    }

    // endregion:   -- Initializer
    // region:      -- Setters

    override suspend fun setDefinitionsUrl(url: String) {
        _configState.update { it.copy(definitionsUrl = url) }
        dataStore.edit { config ->
            config[Constants.DS_KEY_DEFS_URL] = url
        }
    }

    override suspend fun setLanguageName(name: String) {
        _configState.update { it.copy(languageName = name) }
        dataStore.edit { config ->
            config[Constants.DS_KEY_LANG_NAME] = name
        }
    }

    // endregion:   -- Setters

    companion object {
        val DEFAULT_CONFIG_STATE = ConfigState(
            definitionsUrl = Constants.DEFAULT_DEFS_URL,
            languageName = Constants.DEFAULT_LANG_NAME,
        )
    }
}