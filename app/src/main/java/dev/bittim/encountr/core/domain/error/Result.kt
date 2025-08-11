/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Result.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.08.25, 17:37
 */

package dev.bittim.encountr.core.domain.error

sealed interface Result<out D, out E : Error> {
    data class Ok<out D, out E : Error>(val data: D) : Result<D, E>
    data class Err<out D, out E : Error>(val error: E) : Result<D, E>
}