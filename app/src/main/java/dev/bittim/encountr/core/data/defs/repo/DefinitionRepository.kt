/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.09.25, 18:03
 */

package dev.bittim.encountr.core.data.defs.repo

import dev.bittim.encountr.core.data.defs.DefinitionsError
import dev.bittim.encountr.core.domain.error.Result
import dev.bittim.encountr.core.domain.model.defs.IconDefinition
import dev.bittim.encountr.core.domain.model.defs.LinkedVersionGroup

interface DefinitionRepository {
    suspend fun fetchDefinition(urlString: String): Result<Unit, DefinitionsError>
    suspend fun getDefinitionIconPokemon(): Int
    suspend fun getLinkedVersionGroupByParent(parent: Int): LinkedVersionGroup?
    suspend fun getIconByVersion(versionId: Int): IconDefinition?
    suspend fun checkIgnored(game: Int): Boolean
}