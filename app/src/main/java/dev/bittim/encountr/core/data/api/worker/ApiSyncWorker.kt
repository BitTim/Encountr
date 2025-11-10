/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ApiSyncWorker.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
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
import dev.bittim.encountr.core.di.Constants
import dev.bittim.encountr.core.domain.model.api.generation.Generation
import dev.bittim.encountr.core.domain.model.api.language.Language
import dev.bittim.encountr.core.domain.model.api.pokedex.Pokedex
import dev.bittim.encountr.core.domain.model.api.pokemon.Pokemon
import dev.bittim.encountr.core.domain.model.api.type.Type
import dev.bittim.encountr.core.domain.model.api.version.Version
import dev.bittim.encountr.core.domain.model.api.versionGroup.VersionGroup
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

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
    @OptIn(ExperimentalTime::class)
    override suspend fun doWork(): Result {
        // Get input data from params
        val type = inputData.getString(KEY_TYPE)
        val id = inputData.getInt(KEY_ID, -1)
        val force = inputData.getBoolean(KEY_FORCE, false)

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
        if (id == -1) {
            apiDatabase.getIdsOf(type).firstOrNull()?.let { entities ->
                if (entities.isEmpty() || force) repository.refresh()
            }
        } else {
            apiDatabase.getOf(type, id).catch { emit(null) }.firstOrNull().let {
                val expirationDuration = Constants.API_EXPIRATION_DAYS.days
                val expirationTime =
                    Instant.fromEpochSeconds(it?.updatedAt ?: 0) + expirationDuration

                if (expirationTime < Clock.System.now()) repository.refresh(id)
            }
        }

        return Result.success()
    }

    companion object {
        const val KEY_TYPE = "KEY_TYPE"
        const val KEY_ID = "KEY_ID"
        const val KEY_FORCE = "KEY_FORCE"
        const val WORK_BASE_NAME = "ApiSyncWorker"

        fun enqueue(workManager: WorkManager, type: String?, id: Int?, force: Boolean = false) {
            val workRequest = OneTimeWorkRequestBuilder<ApiSyncWorker>().setInputData(
                workDataOf(
                    KEY_TYPE to type,
                    KEY_ID to id,
                    KEY_FORCE to force,
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