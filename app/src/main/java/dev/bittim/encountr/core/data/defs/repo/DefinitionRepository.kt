/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.defs.repo

import dev.bittim.encountr.core.domain.model.api.pokemon.PokemonSpriteVariant

interface DefinitionRepository {
    suspend fun loadDefinition()
    suspend fun getDefinitionIcon(): String?
    suspend fun getPokedexAdditions(versionGroupId: Int): List<Int>?
    suspend fun getVersionIcon(versionId: Int): String?
    suspend fun getVersionSpriteVariant(versionId: Int): PokemonSpriteVariant
    suspend fun isVersionIgnored(versionId: Int): Boolean
}