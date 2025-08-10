/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       RemoteDefinitionsRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.08.25, 02:01
 */

package dev.bittim.encountr.core.data.defs.repo

import dev.bittim.encountr.core.data.defs.DefinitionsDatabase
import dev.bittim.encountr.core.data.defs.entity.Definition

class RemoteDefinitionsRepository(
    private val db: DefinitionsDatabase
) : DefinitionsRepository {
    override suspend fun checkDefinitionsVersion() {
        TODO("Not yet implemented")
    }

    override suspend fun fetchDefinitions() {
        TODO("Not yet implemented")
    }

    override suspend fun getDefinitionByGame(game: String): Definition? {
        return db.definitionDao().getDefinitionByGame(game)
    }
}