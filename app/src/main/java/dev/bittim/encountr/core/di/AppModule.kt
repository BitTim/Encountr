/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       AppModule.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   20.09.25, 03:26
 */

package dev.bittim.encountr.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.config.ConfigStateHolder
import dev.bittim.encountr.core.data.config.ConfigStateHolderImpl
import dev.bittim.encountr.core.data.defs.local.DefinitionsDatabase
import dev.bittim.encountr.core.data.defs.remote.DefinitionKtorService
import dev.bittim.encountr.core.data.defs.remote.DefinitionService
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepository
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepositoryImpl
import dev.bittim.encountr.core.data.pokeapi.repo.LanguagePokeApiRepository
import dev.bittim.encountr.core.data.pokeapi.repo.LanguageRepository
import dev.bittim.encountr.core.data.pokeapi.repo.PokedexPokeApiRepository
import dev.bittim.encountr.core.data.pokeapi.repo.PokedexRepository
import dev.bittim.encountr.core.data.pokeapi.repo.PokemonOverviewPokeApiRepository
import dev.bittim.encountr.core.data.pokeapi.repo.PokemonOverviewRepository
import dev.bittim.encountr.core.data.pokeapi.repo.PokemonSpeciesPokeApiRepository
import dev.bittim.encountr.core.data.pokeapi.repo.PokemonSpeciesRepository
import dev.bittim.encountr.core.data.pokeapi.repo.PokemonVarietyPokeApiRepository
import dev.bittim.encountr.core.data.pokeapi.repo.PokemonVarietyRepository
import dev.bittim.encountr.core.data.pokeapi.repo.TypePokeApiRepository
import dev.bittim.encountr.core.data.pokeapi.repo.TypeRepository
import dev.bittim.encountr.core.data.pokeapi.repo.VersionPokeApiRepository
import dev.bittim.encountr.core.data.pokeapi.repo.VersionRepository
import dev.bittim.encountr.core.data.user.local.UserDatabase
import dev.bittim.encountr.core.data.user.repo.PokemonRepository
import dev.bittim.encountr.core.data.user.repo.PokemonRepositoryImpl
import dev.bittim.encountr.core.data.user.repo.SaveRepository
import dev.bittim.encountr.core.data.user.repo.SaveRepositoryImpl
import dev.bittim.encountr.core.data.user.repo.TeamRepository
import dev.bittim.encountr.core.data.user.repo.TeamRepositoryImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.cache.storage.FileStorage
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
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
    single<DefinitionService> { DefinitionKtorService(get()) }

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
            UserDatabase::class.java,
            "user"
        ).build()
    }

    // endregion:   -- Databases
    // region:      -- Repositories

    single<DefinitionRepository> { DefinitionRepositoryImpl(get(), get()) }

    single<LanguageRepository> { LanguagePokeApiRepository(get()) }
    single<PokemonVarietyRepository> { PokemonVarietyPokeApiRepository(get()) }
    single<PokemonSpeciesRepository> { PokemonSpeciesPokeApiRepository(get()) }
    single<PokedexRepository> { PokedexPokeApiRepository(get()) }
    single<VersionRepository> { VersionPokeApiRepository(get(), get(), get()) }
    single<TypeRepository> { TypePokeApiRepository(get()) }
    single<PokemonOverviewRepository> {
        PokemonOverviewPokeApiRepository(
            get(),
            get(),
            get(),
            get()
        )
    }

    single<PokemonRepository> { PokemonRepositoryImpl(get()) }
    single<SaveRepository> { SaveRepositoryImpl(get()) }
    single<TeamRepository> { TeamRepositoryImpl(get()) }

    // endregion:   -- Repositories
}