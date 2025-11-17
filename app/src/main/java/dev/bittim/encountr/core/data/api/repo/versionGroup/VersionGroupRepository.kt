/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionGroupRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 02:31
 */

package dev.bittim.encountr.core.data.api.repo.versionGroup

import dev.bittim.encountr.core.data.api.repo.Repository
import dev.bittim.encountr.core.domain.model.api.versionGroup.VersionGroup
import kotlinx.coroutines.flow.Flow

interface VersionGroupRepository : Repository<VersionGroup> {
    fun getVersionIds(id: Int): Flow<List<Int>>
    fun getPokedexIds(id: Int): Flow<List<Int>>
}