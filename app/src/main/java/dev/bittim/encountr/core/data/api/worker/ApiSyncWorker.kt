/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ApiSyncWorker.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   09.11.25, 01:05
 */

package dev.bittim.encountr.core.data.api.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.repo.generation.GenerationRepository
import dev.bittim.encountr.core.data.api.repo.language.LanguageRepository
import dev.bittim.encountr.core.data.api.repo.pokedex.PokedexRepository
import dev.bittim.encountr.core.data.api.repo.pokemon.PokemonRepository
import dev.bittim.encountr.core.data.api.repo.type.TypeRepository
import dev.bittim.encountr.core.data.api.repo.version.VersionRepository
import dev.bittim.encountr.core.data.api.repo.versionGroup.VersionGroupRepository
import dev.bittim.encountr.core.domain.model.api.language.Language
import dev.bittim.encountr.core.domain.model.api.pokedex.Pokedex
import dev.bittim.encountr.core.domain.model.api.pokemon.Pokemon
import dev.bittim.encountr.core.domain.model.api.type.Type
import dev.bittim.encountr.core.domain.model.api.version.Generation
import dev.bittim.encountr.core.domain.model.api.version.Version
import dev.bittim.encountr.core.domain.model.api.version.VersionGroup
import kotlinx.coroutines.flow.firstOrNull
import java.time.OffsetDateTime

class ApiSyncWorker(
    context: Context,
    params: WorkerParameters,
    private val apiDatabase: ApiDatabase,
    private val generationRepository: GenerationRepository,
    private val languageRepository: LanguageRepository,
    private val pokedexRepository: PokedexRepository,
    private val pokemonRepository: PokemonRepository,
    private val typeRepository: TypeRepository,
    private val versionRepository: VersionRepository,
    private val versionGroupRepository: VersionGroupRepository,
) : CoroutineWorker(
    appContext = context,
    params = params
) {
    override suspend fun doWork(): Result {
        // Get input data from params
        val type = inputData.getString(KEY_TYPE)
        val id = inputData.getInt(KEY_ID, -1)

        // Get repository that shall be used
        val repository = when (type) {
            Generation::class.simpleName -> generationRepository
            Language::class.simpleName -> languageRepository
            Pokedex::class.simpleName -> pokedexRepository
            Pokemon::class.simpleName -> pokemonRepository
            Type::class.simpleName -> typeRepository
            Version::class.simpleName -> versionRepository
            VersionGroup::class.simpleName -> versionGroupRepository

            else -> return Result.failure()
        }

        // Check expiry and fetch from API if expired
        val currentTime = OffsetDateTime.now().toEpochSecond()

        if (id == -1) {
            apiDatabase.getOf(type).firstOrNull()?.let { entities ->
                if (entities.isEmpty() || entities.any { it.expiresAt == null || it.expiresAt!! < currentTime }) repository.refresh()
            }
        } else {
            apiDatabase.getOf(type, id).firstOrNull().let {
                if (it == null || it.expiresAt == null || it.expiresAt!! < currentTime) repository.refresh(
                    id
                )
            }
        }

        return Result.success()
    }

    companion object {
        const val KEY_TYPE = "KEY_TYPE"
        const val KEY_ID = "KEY_ID"
        const val WORK_BASE_NAME = "ApiSyncWorker"

        fun enqueue(workManager: WorkManager, type: String?, id: Int?) {
            val workRequest = OneTimeWorkRequestBuilder<ApiSyncWorker>().setInputData(
                workDataOf(
                    KEY_TYPE to type,
                    KEY_ID to id,
                )
            ).setConstraints(Constraints(NetworkType.CONNECTED)).build()
            workManager.enqueueUniqueWork(
                "${WORK_BASE_NAME}_${type}_${id}",
                ExistingWorkPolicy.KEEP,
                workRequest
            )
        }
    }
}