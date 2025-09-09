/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GameError.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.09.25, 00:07
 */

package dev.bittim.encountr.core.data.pokeapi

import dev.bittim.encountr.core.domain.error.Error

interface GameError : Error {
    data class InvalidVersionGroup(val versionGroup: String) : GameError, Throwable()
    data class InvalidVersion(val version: String) : GameError, Throwable()
}