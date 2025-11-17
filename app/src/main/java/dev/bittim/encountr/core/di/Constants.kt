/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Constants.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 02:31
 */

package dev.bittim.encountr.core.di

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.bittim.encountr.core.domain.model.api.pokemon.PokemonSpriteVariant

object Constants {
    // region:      -- URLs

    const val GITHUB_URL = "https://github.com/BitTim/Encountr"

    // endregion:   -- URLs
    // region:      -- Default Values

    const val DEFAULT_LANG_ID = 9

    // endregion:   -- Default Values
    // region:      -- DataStore Keys

    const val DS_NAME = "EncountrConfig"
    val DS_KEY_ONBOARDING_COMPLETED = booleanPreferencesKey("onboardingCompleted")
    val DS_KEY_LANG_ID = intPreferencesKey("languageId")
    val DS_KEY_CURR_SAVE_UUID = stringPreferencesKey("currentSaveUuid")

    // endregion:   -- DataStore Keys
    // region:      -- Expiration Durations

    const val API_EXPIRATION_DAYS: Long = 30

    // endregion:   -- Expiration Durations
    // region:      -- Sprites

    val DEFAULT_POKEMON_SPRITE_VARIANT = PokemonSpriteVariant.DEFAULT

    // endregion:   -- Sprites
}
