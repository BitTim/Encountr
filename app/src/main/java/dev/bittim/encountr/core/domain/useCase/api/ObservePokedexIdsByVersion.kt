/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ObservePokedexIdsByVersion.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 02:31
 */

package dev.bittim.encountr.core.domain.useCase.api

import dev.bittim.encountr.core.data.api.repo.version.VersionRepository
import dev.bittim.encountr.core.data.api.repo.versionGroup.VersionGroupRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf

class ObservePokedexIdsByVersion(
    private val versionRepository: VersionRepository,
    private val versionGroupRepository: VersionGroupRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(versionId: Int): Flow<List<Int>> {
        return versionRepository.get(versionId).flatMapLatest { version ->
            val versionGroupId = version?.versionGroupId
            if (versionGroupId != null) versionGroupRepository.getPokedexIds(versionGroupId)
            else flowOf(emptyList())
        }
    }
}