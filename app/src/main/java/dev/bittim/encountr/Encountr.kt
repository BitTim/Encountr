/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Encountr.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr

import android.app.Application
import android.util.Log
import dev.bittim.encountr.content.di.contentModule
import dev.bittim.encountr.core.data.config.ConfigStateHolder
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepository
import dev.bittim.encountr.core.di.appModule
import dev.bittim.encountr.onboarding.di.onboardingModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.startKoin

class Encountr : Application() {
    private val ioAppScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Encountr)
            workManagerFactory()
            modules(appModule, onboardingModule, contentModule)
        }

        fetchDefinitions()
    }

    private fun fetchDefinitions() {
        val koin = GlobalContext.get()
        val configStateHolder = koin.get<ConfigStateHolder>()

        runBlocking {
            Log.d("fetchDefinitions", "fetchDefinitions")
            configStateHolder.init()
            configStateHolder.state
            Log.d(
                "fetchDefinitions",
                "configStateHolder.state.value: ${configStateHolder.state.value}"
            )
        }

        ioAppScope.launch {
            val definitionRepository = GlobalContext.get().get<DefinitionRepository>()
            definitionRepository.loadDefinition()
        }
    }
}