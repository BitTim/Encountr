/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionRepositoryImpl.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 19:39
 */

package dev.bittim.encountr.core.data.defs.repo

import dev.bittim.encountr.core.data.defs.file.DefinitionLoader
import dev.bittim.encountr.core.data.defs.local.DefinitionsDatabase
import dev.bittim.encountr.core.domain.model.defs.VersionAddition

class DefinitionRepositoryImpl(
    private val db: DefinitionsDatabase,
    private val loader: DefinitionLoader
) : DefinitionRepository {
    override suspend fun loadDefinition() {
        val definition = loader.get()

        db.definitionDao().delete()
        db.definitionDao().upsert(listOf(definition.toEntity()))
        db.pokedexAdditionDao()
            .upsert(definition.pokedexAdditions.map { it.toEntity() })
        db.versionAdditionDao().upsert(definition.versionAdditions.map { it.toEntity() })
    }

    override suspend fun getDefinitionIcon(): String? {
        return db.definitionDao().get()?.imageUrl
    }

    override suspend fun getPokedexAdditions(versionGroupId: Int): List<Int>? {
        return db.pokedexAdditionDao().get(versionGroupId)?.pokedexIds
    }

    override suspend fun getVersionAdditions(versionId: Int): VersionAddition? {
        return db.versionAdditionDao().get(versionId)?.toModel()
    }

    override suspend fun isVersionIgnored(versionId: Int): Boolean {
        return db.definitionDao().get()?.ignoredVersionIds?.contains(versionId) == true
    }
}