/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LanguageRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   12.09.25, 16:44
 */

package dev.bittim.encountr.core.data.pokeapi.repo

import dev.bittim.encountr.core.domain.model.pokeapi.Language

interface LanguageRepository {
    suspend fun get(id: Int): Language?
    suspend fun get(name: String): Language?

    suspend fun getAll(): List<Language>
}