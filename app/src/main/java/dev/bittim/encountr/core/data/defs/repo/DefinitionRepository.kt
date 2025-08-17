/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.08.25, 03:16
 */

package dev.bittim.encountr.core.data.defs.repo

import dev.bittim.encountr.core.data.defs.DefinitionsError
import dev.bittim.encountr.core.domain.error.Result
import dev.bittim.encountr.core.domain.model.defs.IconDefinition

interface DefinitionRepository {
    suspend fun fetchDefinitions(urlString: String): Result<Unit, DefinitionsError>
    suspend fun getIconByGame(game: Int): IconDefinition?
    suspend fun checkIgnored(game: Int): Boolean
}