/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionRepositoryImpl.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   06.09.25, 02:27
 */

package dev.bittim.encountr.core.data.defs.repo

import dev.bittim.encountr.core.data.defs.DefinitionsError
import dev.bittim.encountr.core.data.defs.local.DefinitionsDatabase
import dev.bittim.encountr.core.data.defs.remote.DefinitionService
import dev.bittim.encountr.core.domain.error.Result
import dev.bittim.encountr.core.domain.model.defs.IconDefinition
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

class DefinitionRepositoryImpl(
    private val db: DefinitionsDatabase,
    private val api: DefinitionService
) : DefinitionRepository {
    override suspend fun fetchDefinition(urlString: String): Result<Unit, DefinitionsError> {
        val response = api.getDefinitions(urlString)
        val definition = when (response) {
            is Result.Ok -> response.data
            is Result.Err -> {
                return Result.Err(response.error)
            }
        }

        try {
            db.definitionDao().deleteAll()
            db.definitionDao().insert(listOf(definition.toEntity()))
            db.iconDao().insert(definition.icons.mapIndexed { idx, dto -> dto.toEntity(idx) })
        } catch (_: Exception) {
            coroutineContext.ensureActive()
            return Result.Err(DefinitionsError.Cache)
        }

        return Result.Ok(Unit)
    }

    override suspend fun getDefinitionIconPokemon(): Int {
        return db.definitionDao().getDefinition()?.iconPokemon ?: 0
    }

    override suspend fun getIconByGame(game: Int): IconDefinition? {
        return db.iconDao().getDefinition(game)?.toModel()
    }

    override suspend fun checkIgnored(game: Int): Boolean {
        return db.definitionDao().getDefinition()?.ignored?.contains(game) == true
    }
}