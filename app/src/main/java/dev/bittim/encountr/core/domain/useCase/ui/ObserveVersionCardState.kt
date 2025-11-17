/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ObserveVersionCardState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 02:31
 */

package dev.bittim.encountr.core.domain.useCase.ui

import dev.bittim.encountr.core.data.api.repo.generation.GenerationRepository
import dev.bittim.encountr.core.data.api.repo.version.VersionRepository
import dev.bittim.encountr.core.data.api.repo.versionGroup.VersionGroupRepository
import dev.bittim.encountr.core.domain.useCase.util.ObserveLocalizedName
import dev.bittim.encountr.core.ui.components.version.versionCard.VersionCardState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn

class ObserveVersionCardState(
    private val versionRepository: VersionRepository,
    private val versionGroupRepository: VersionGroupRepository,
    private val generationRepository: GenerationRepository,
    private val observeLocalizedName: ObserveLocalizedName
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(versionId: Int): Flow<VersionCardState?> {
        return versionRepository.get(versionId).flatMapLatest { version ->
            val versionGroupId = version?.versionGroupId
            val versionGroupFlow =
                if (versionGroupId != null) versionGroupRepository.get(versionGroupId)
                else flowOf(null)

            versionGroupFlow.flatMapLatest { versionGroup ->
                val generationId = versionGroup?.generationId
                val generationFlow =
                    if (generationId != null) generationRepository.get(generationId)
                    else flowOf(null)

                generationFlow.flatMapLatest { generation ->
                    val nameFlow = observeLocalizedName(version?.localizedNames, version?.name)
                    val generationNameFlow =
                        observeLocalizedName(generation?.localizedNames, generation?.name)

                    nameFlow.combine(generationNameFlow) { name, generationName ->
                        VersionCardState(
                            name = name,
                            generation = generationName,
                            imageUrl = version?.imageUrl
                        )
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}