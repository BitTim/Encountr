/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ObserveVersionCardState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.11.25, 02:45
 */

package dev.bittim.encountr.core.domain.useCase.ui

import android.util.Log
import dev.bittim.encountr.core.data.api.repo.generation.GenerationRepository
import dev.bittim.encountr.core.data.api.repo.version.VersionRepository
import dev.bittim.encountr.core.data.api.repo.versionGroup.VersionGroupRepository
import dev.bittim.encountr.core.domain.useCase.config.ObserveCurrentLanguageId
import dev.bittim.encountr.core.domain.useCase.util.FindLocalizedName
import dev.bittim.encountr.core.ui.components.version.versionCard.VersionCardState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class ObserveVersionCardState(
    private val versionRepository: VersionRepository,
    private val versionGroupRepository: VersionGroupRepository,
    private val generationRepository: GenerationRepository,
    private val observeCurrentLanguageId: ObserveCurrentLanguageId,
    private val findLocalizedName: FindLocalizedName
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(versionId: Int): Flow<VersionCardState?> {
        val languageFlow = observeCurrentLanguageId()
        val versionFlow = versionRepository.get(versionId)

        return combine(languageFlow, versionFlow) { languageId, version ->
            Log.d("ObserveVersionCard", "[$versionId] LanguageId: $languageId")
            Log.d("ObserveVersionCard", "[$versionId] Version: $version")

            val versionGroupId = version?.versionGroupId
            val versionGroupFlow =
                if (versionGroupId != null) versionGroupRepository.get(versionGroupId)
                else flowOf(null)

            versionGroupFlow.flatMapLatest { versionGroup ->
                Log.d("ObserveVersionCard", "[$versionId] VersionGroup: $versionGroup")

                val generationId = versionGroup?.generationId
                val generationFlow =
                    if (generationId != null) generationRepository.get(generationId)
                    else flowOf(null)

                generationFlow.map { generation ->
                    Log.d("ObserveVersionCard", "[$versionId] Generation: $generation")

                    if (version == null || versionGroup == null || generation == null) return@map null

                    VersionCardState(
                        name = findLocalizedName(
                            version.localizedNames,
                            languageId,
                            version.name
                        ),
                        generation = findLocalizedName(
                            generation.localizedNames,
                            languageId,
                            generation.name
                        ),
                        imageUrl = version.imageUrl
                    )
                }
            }
        }.flatMapLatest { it }.flowOn(Dispatchers.IO)
    }
}