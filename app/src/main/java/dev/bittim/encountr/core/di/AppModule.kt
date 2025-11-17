/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       AppModule.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 02:31
 */

package dev.bittim.encountr.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import androidx.work.WorkManager
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.api.local.ApiDatabase
import dev.bittim.encountr.core.data.api.repo.generation.GenerationPokeApiRepository
import dev.bittim.encountr.core.data.api.repo.generation.GenerationRepository
import dev.bittim.encountr.core.data.api.repo.language.LanguagePokeApiRepository
import dev.bittim.encountr.core.data.api.repo.language.LanguageRepository
import dev.bittim.encountr.core.data.api.repo.pokedex.PokedexPokeApiRepository
import dev.bittim.encountr.core.data.api.repo.pokedex.PokedexRepository
import dev.bittim.encountr.core.data.api.repo.pokemon.PokemonPokeApiRepository
import dev.bittim.encountr.core.data.api.repo.pokemon.PokemonRepository
import dev.bittim.encountr.core.data.api.repo.type.TypePokeApiRepository
import dev.bittim.encountr.core.data.api.repo.type.TypeRepository
import dev.bittim.encountr.core.data.api.repo.version.VersionPokeApiRepository
import dev.bittim.encountr.core.data.api.repo.version.VersionRepository
import dev.bittim.encountr.core.data.api.repo.versionGroup.VersionGroupPokeApiRepository
import dev.bittim.encountr.core.data.api.repo.versionGroup.VersionGroupRepository
import dev.bittim.encountr.core.data.api.worker.ApiSyncWorker
import dev.bittim.encountr.core.data.config.ConfigStateHolder
import dev.bittim.encountr.core.data.config.ConfigStateHolderImpl
import dev.bittim.encountr.core.data.defs.file.DefinitionFileLoader
import dev.bittim.encountr.core.data.defs.file.DefinitionLoader
import dev.bittim.encountr.core.data.defs.local.DefinitionsDatabase
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepository
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepositoryImpl
import dev.bittim.encountr.core.data.user.local.UserDatabase
import dev.bittim.encountr.core.data.user.repo.PokemonStateRepository
import dev.bittim.encountr.core.data.user.repo.PokemonStateRepositoryImpl
import dev.bittim.encountr.core.data.user.repo.SaveRepository
import dev.bittim.encountr.core.data.user.repo.SaveRepositoryImpl
import dev.bittim.encountr.core.data.user.repo.TeamRepository
import dev.bittim.encountr.core.data.user.repo.TeamRepositoryImpl
import dev.bittim.encountr.core.domain.useCase.api.ObservePokedexIdsByVersion
import dev.bittim.encountr.core.domain.useCase.api.ObserveVersionIdsByGeneration
import dev.bittim.encountr.core.domain.useCase.config.ObserveCurrentLanguageId
import dev.bittim.encountr.core.domain.useCase.config.ObserveCurrentVersion
import dev.bittim.encountr.core.domain.useCase.config.ObserveIsOnboarded
import dev.bittim.encountr.core.domain.useCase.ui.ObserveLanguageCardState
import dev.bittim.encountr.core.domain.useCase.ui.ObservePokedexName
import dev.bittim.encountr.core.domain.useCase.ui.ObserveVersionCardState
import dev.bittim.encountr.core.domain.useCase.util.ObserveLocalizedName
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.cache.storage.FileStorage
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.dsl.module
import java.io.File

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.DS_NAME)

val appModule = module {
    // region:      -- Other

    // SharedPreferences
    single {
        androidContext().dataStore
    }

    // Config State
    single<ConfigStateHolder> { ConfigStateHolderImpl(get(), get()) }

    // Work Manager
    single<WorkManager> { WorkManager.getInstance(androidContext()) }

    // endregion:   -- Other
    // region:      -- Networking

    single<HttpClientEngine> {
        Android.create {
            dispatcher = Dispatchers.IO
        }
    }

    single {
        HttpClient(engine = get()) {
            install(ContentNegotiation) {
                json()
            }
        }
    }

    // endregion:   -- Networking
    // region:      -- Services

    single<PokeApi> {
        PokeApi.Custom(
            engine = get(),
            cacheStorage = FileStorage(File(androidContext().cacheDir, "pokeapi_cache")),
        )
    }
    single<DefinitionLoader> { DefinitionFileLoader(get()) }

    // endregion:   -- Services
    // region:      -- Databases

    single {
        Room.databaseBuilder(
            androidApplication(),
            DefinitionsDatabase::class.java,
            "definitions"
        ).build()
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            ApiDatabase::class.java,
            "api"
        ).build()
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            UserDatabase::class.java,
            "user"
        ).build()
    }

    // endregion:   -- Databases
    // region:      -- Repositories

    single<DefinitionRepository> { DefinitionRepositoryImpl(get(), get()) }

    single<GenerationRepository> { GenerationPokeApiRepository(get(), get(), get()) }
    single<LanguageRepository> { LanguagePokeApiRepository(get(), get(), get()) }
    single<PokedexRepository> { PokedexPokeApiRepository(get(), get(), get()) }
    single<PokemonRepository> { PokemonPokeApiRepository(get(), get(), get()) }
    single<TypeRepository> { TypePokeApiRepository(get(), get(), get()) }
    single<VersionRepository> { VersionPokeApiRepository(get(), get(), get(), get()) }
    single<VersionGroupRepository> { VersionGroupPokeApiRepository(get(), get(), get(), get()) }

    single<PokemonStateRepository> { PokemonStateRepositoryImpl(get()) }
    single<SaveRepository> { SaveRepositoryImpl(get()) }
    single<TeamRepository> { TeamRepositoryImpl(get()) }

    // endregion:   -- Repositories
    // region:      -- UseCases

    // Config
    single<ObserveCurrentLanguageId> { ObserveCurrentLanguageId(get()) }
    single<ObserveCurrentVersion> { ObserveCurrentVersion(get(), get(), get()) }
    single<ObserveIsOnboarded> { ObserveIsOnboarded(get()) }

    // Util
    single<ObserveLocalizedName> { ObserveLocalizedName(get()) }

    // API
    single<ObserveVersionIdsByGeneration> { ObserveVersionIdsByGeneration(get(), get()) }
    single<ObservePokedexIdsByVersion> { ObservePokedexIdsByVersion(get(), get()) }

    // UI
    single<ObservePokedexName> { ObservePokedexName(get(), get()) }
    single<ObserveLanguageCardState> { ObserveLanguageCardState(get()) }
    single<ObserveVersionCardState> { ObserveVersionCardState(get(), get(), get(), get()) }

    // endregion:   -- UseCases
    // region:      -- Workers

    workerOf(::ApiSyncWorker)

    // endregion:   -- Workers
}