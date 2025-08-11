/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionRepositoryImpl.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.08.25, 17:38
 */

package dev.bittim.encountr.core.data.defs.repo

import androidx.room.withTransaction
import dev.bittim.encountr.core.data.defs.DefinitionsError
import dev.bittim.encountr.core.data.defs.local.DefinitionsDatabase
import dev.bittim.encountr.core.data.defs.remote.DefinitionService
import dev.bittim.encountr.core.domain.error.Result
import dev.bittim.encountr.core.domain.model.defs.Definition

class DefinitionRepositoryImpl(
    private val db: DefinitionsDatabase,
    private val api: DefinitionService
) : DefinitionRepository {
    override suspend fun fetchDefinitions(urlString: String): Result<Unit, DefinitionsError> {
        val response = api.getDefinitions(urlString)
        val definitions = when (response) {
            is Result.Ok -> response.data
            is Result.Err -> {
                return Result.Err(response.error)
            }
        }

        try {
            db.withTransaction {
                db.definitionDao().deleteAll()
                db.definitionDao().insert(definitions.definitions.map { it.toEntity() })
            }
        } catch (_: Exception) {
            return Result.Err(DefinitionsError.Cache)
        }

        return Result.Ok(Unit)
    }

    override suspend fun getDefinitionByGame(game: String): Definition? {
        return db.definitionDao().getDefinition(game)?.toModel()
    }
}