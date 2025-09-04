/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Constants.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   04.09.25, 18:06
 */

package dev.bittim.encountr.core.di

import androidx.datastore.preferences.core.stringPreferencesKey

object Constants {
    // region:      -- URLs

    const val GITHUB_URL = "https://github.com/BitTim/Encountr"

    // endregion:   -- URLs
    // region:      -- Default Values

    const val DEFAULT_DEFS_URL = "https://bittim.github.io/Encountr/definitions.json"
    const val DEFAULT_LANG_NAME = "en"

    // endregion:   -- Default Values
    // region:      -- DataStore Keys

    const val DS_NAME = "EncountrConfig"
    val DS_KEY_DEFS_URL = stringPreferencesKey("definitionsUrl")
    val DS_KEY_LANG_NAME = stringPreferencesKey("languageName")

    // endregion:   -- DataStore Keys
}