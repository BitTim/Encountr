/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       AppModule.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.08.25, 17:37
 */

package dev.bittim.encountr.core.di

import android.content.Context
import androidx.room.Room
import dev.bittim.encountr.core.data.defs.local.DefinitionsDatabase
import dev.bittim.encountr.core.data.defs.remote.DefinitionKtorService
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepository
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepositoryImpl
import dev.bittim.encountr.onboarding.ui.OnboardingViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    // region:      -- Networking

    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json()
            }
        }
    }

    // endregion:   -- Networking
    // region:      -- Services

    single { DefinitionKtorService(get()) }

    // endregion:   -- Services
    // region:      -- Databases

    single {
        Room.databaseBuilder(
            androidApplication(),
            DefinitionsDatabase::class.java,
            "definitions"
        ).build()
    }

    // endregion:   -- Databases
    // region:      -- Repositories

    single<DefinitionRepository> { DefinitionRepositoryImpl(get(), get()) }

    // endregion:   -- Repositories
    // region:      -- ViewModels

    viewModelOf(::OnboardingViewModel)

    // endregion:   -- ViewModels
    // region:      -- Other

    // SharedPreferences
    single {
        androidApplication().getSharedPreferences(
            Constants.SHARED_PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    // endregion:   -- Other
}