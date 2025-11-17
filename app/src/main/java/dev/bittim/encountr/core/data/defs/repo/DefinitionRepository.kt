/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 19:39
 */

package dev.bittim.encountr.core.data.defs.repo

import dev.bittim.encountr.core.domain.model.defs.VersionAddition

interface DefinitionRepository {
    suspend fun loadDefinition()
    suspend fun getDefinitionIcon(): String?
    suspend fun getPokedexAdditions(versionGroupId: Int): List<Int>?
    suspend fun getVersionAdditions(versionId: Int): VersionAddition?
    suspend fun isVersionIgnored(versionId: Int): Boolean
}