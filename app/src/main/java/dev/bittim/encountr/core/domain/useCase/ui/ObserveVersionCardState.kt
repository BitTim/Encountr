/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ObserveVersionCardState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   13.11.25, 17:09
 */

package dev.bittim.encountr.core.domain.useCase.ui

import dev.bittim.encountr.core.data.api.repo.generation.GenerationRepository
import dev.bittim.encountr.core.data.api.repo.version.VersionRepository
import dev.bittim.encountr.core.data.api.repo.versionGroup.VersionGroupRepository
import dev.bittim.encountr.core.domain.useCase.config.ObserveCurrentLanguageId
import dev.bittim.encountr.core.domain.useCase.util.FindLocalizedName
import dev.bittim.encountr.core.ui.components.version.versionCard.VersionCardState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
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
        return observeCurrentLanguageId().flatMapLatest { languageId ->
            versionRepository.get(versionId).flatMapLatest { version ->
                versionGroupRepository.get(
                    version?.versionGroupId ?: return@flatMapLatest flowOf(
                        null
                    )
                )
                    .flatMapLatest { versionGroup ->
                        generationRepository.get(
                            versionGroup?.generationId ?: return@flatMapLatest flowOf(null)
                        ).map { generation ->
                            if (generation == null) return@map null

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
            }
        }
    }
}