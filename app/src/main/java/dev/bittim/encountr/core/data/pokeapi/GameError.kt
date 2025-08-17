/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GameError.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.08.25, 21:41
 */

package dev.bittim.encountr.core.data.pokeapi

import dev.bittim.encountr.core.domain.error.Error

interface GameError : Error {
    data class InvalidVersionGroup(val versionGroup: String) : GameError
    data class InvalidVersion(val version: String) : GameError
}