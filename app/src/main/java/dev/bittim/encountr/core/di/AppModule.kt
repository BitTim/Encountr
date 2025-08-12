/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       AppModule.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   12.08.25, 01:11
 */

package dev.bittim.encountr.core.di

import android.content.Context
import androidx.room.Room
import dev.bittim.encountr.core.data.defs.local.DefinitionsDatabase
import dev.bittim.encountr.core.data.defs.remote.DefinitionKtorService
import dev.bittim.encountr.core.data.defs.remote.DefinitionService
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepository
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepositoryImpl
import dev.bittim.encountr.onboarding.ui.screens.url.OnboardingUrlViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    // region:      -- Other

    // SharedPreferences
    single {
        androidApplication().getSharedPreferences(
            Constants.SPNAME,
            Context.MODE_PRIVATE
        )
    }

    // endregion:   -- Other
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

    // endregion:   -- Databases
    // region:      -- Repositories

    single<DefinitionRepository> { DefinitionRepositoryImpl(get(), get()) }

    // endregion:   -- Repositories
    // region:      -- ViewModels

    viewModelOf(::OnboardingUrlViewModel)

    // endregion:   -- ViewModels
}