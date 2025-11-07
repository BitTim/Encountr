/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       GetVersionsByGeneration.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.domain.useCase.api

import dev.bittim.encountr.core.data.api.repo.generation.GenerationRepository
import dev.bittim.encountr.core.data.api.repo.version.VersionRepository
import dev.bittim.encountr.core.data.api.repo.versionGroup.VersionGroupRepository
import dev.bittim.encountr.core.domain.model.api.version.Version
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class GetVersionsByGeneration(
    private val generationRepository: GenerationRepository,
    private val versionGroupRepository: VersionGroupRepository,
    private val versionRepository: VersionRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(generationId: Int): Flow<List<Version?>> {
        val generationFlow = generationRepository.get(generationId)

        val versionGroupListFlow =
            generationFlow.filterNotNull().map { it.versionGroups }.distinctUntilChanged()
                .flatMapLatest { versionGroupIds ->
                    if (versionGroupIds.isEmpty()) {
                        flowOf(emptyList())
                    } else {
                        val versionGroupFlows =
                            versionGroupIds.map { handle -> versionGroupRepository.get(handle.id) }
                        combine(versionGroupFlows) { it.toList() }
                    }
                }

        val versionListFlow = versionGroupListFlow.flatMapLatest { versionGroupList ->
            if (versionGroupList.isEmpty()) {
                flowOf(emptyList())
            } else {
                val versionGroupFlows = versionGroupList.filterNotNull().map { versionGroup ->
                    flowOf(versionGroup.versions).distinctUntilChanged()
                        .flatMapLatest { versionIds ->
                            if (versionIds.isEmpty()) {
                                flowOf(emptyList())
                            } else {
                                val versionFlows = versionIds.map { handle ->
                                    versionRepository.get(handle.id)
                                }
                                combine(versionFlows) { it.toList() }
                            }
                        }
                }

                combine(versionGroupFlows) { versionLists -> versionLists.flatMap { it } }
            }
        }

        return versionListFlow
    }
}