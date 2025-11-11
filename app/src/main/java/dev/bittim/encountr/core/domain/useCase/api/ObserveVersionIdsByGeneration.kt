/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ObserveVersionIdsByGeneration.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.11.25, 15:50
 */

package dev.bittim.encountr.core.domain.useCase.api

import dev.bittim.encountr.core.data.api.repo.generation.GenerationRepository
import dev.bittim.encountr.core.data.api.repo.versionGroup.VersionGroupRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

class ObserveVersionIdsByGeneration(
    private val generationRepository: GenerationRepository,
    private val versionGroupRepository: VersionGroupRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(generationId: Int): Flow<List<Int>> {
        return generationRepository.getVersionGroupIds(generationId)
            .flatMapLatest { versionGroupIds ->
                val versionIdsFlows = versionGroupIds.map {
                    versionGroupRepository.getVersionIds(it)
                }
                combine(versionIdsFlows) { it.toList().flatten() }
            }
    }
}