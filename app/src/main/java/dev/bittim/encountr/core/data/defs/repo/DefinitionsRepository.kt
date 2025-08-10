/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionsRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.08.25, 02:01
 */

package dev.bittim.encountr.core.data.defs.repo

import dev.bittim.encountr.core.data.defs.entity.Definition

interface DefinitionsRepository {
    suspend fun checkDefinitionsVersion()
    suspend fun fetchDefinitions()

    suspend fun getDefinitionByGame(game: String): Definition?
}