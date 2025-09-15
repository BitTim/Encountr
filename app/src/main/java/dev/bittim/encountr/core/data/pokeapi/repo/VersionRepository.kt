/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.09.25, 18:03
 */

package dev.bittim.encountr.core.data.pokeapi.repo

import co.pokeapi.pokekotlin.model.Generation
import dev.bittim.encountr.core.domain.model.pokeapi.Version

interface VersionRepository {
    suspend fun get(
        id: Int,
        providedRawGeneration: Generation? = null
    ): Version?

    suspend fun getByGeneration(generationId: Int): List<Version>
}