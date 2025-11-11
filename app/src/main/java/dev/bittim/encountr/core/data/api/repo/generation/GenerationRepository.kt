/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GenerationRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.11.25, 15:50
 */

package dev.bittim.encountr.core.data.api.repo.generation

import dev.bittim.encountr.core.data.api.repo.Repository
import dev.bittim.encountr.core.domain.model.api.generation.Generation
import kotlinx.coroutines.flow.Flow

interface GenerationRepository : Repository<Generation> {
    fun getVersionGroupIds(id: Int): Flow<List<Int>>
}