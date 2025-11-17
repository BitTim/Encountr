/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ObserveCurrentVersion.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 02:31
 */

package dev.bittim.encountr.core.domain.useCase.config

import dev.bittim.encountr.core.data.api.repo.version.VersionRepository
import dev.bittim.encountr.core.data.config.ConfigStateHolder
import dev.bittim.encountr.core.data.user.repo.SaveRepository
import dev.bittim.encountr.core.domain.model.api.version.Version
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlin.uuid.ExperimentalUuidApi

class ObserveCurrentVersion(
    private val configStateHolder: ConfigStateHolder,
    private val saveRepository: SaveRepository,
    private val versionRepository: VersionRepository
) {
    @OptIn(ExperimentalUuidApi::class, ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<Version?> {
        return configStateHolder.rawState.map { it?.currentSaveUuid }.flatMapLatest { saveUuid ->
            val saveFlow = if (saveUuid != null) saveRepository.get(saveUuid) else flowOf(null)
            saveFlow.flatMapLatest { save ->
                if (save != null) versionRepository.get(save.version) else flowOf(null)
            }
        }.distinctUntilChanged().flowOn(Dispatchers.IO)
    }
}