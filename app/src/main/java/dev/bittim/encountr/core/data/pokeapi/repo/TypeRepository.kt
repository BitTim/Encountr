/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TypeRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.09.25, 00:53
 */

package dev.bittim.encountr.core.data.pokeapi.repo

import dev.bittim.encountr.core.domain.model.pokeapi.Type

interface TypeRepository {
    suspend fun get(id: Int): Type?
}