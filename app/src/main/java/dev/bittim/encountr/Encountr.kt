/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Encountr.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.08.25, 17:54
 */

package dev.bittim.encountr

import android.app.Application
import android.content.SharedPreferences
import dev.bittim.encountr.core.data.defs.repo.DefinitionRepository
import dev.bittim.encountr.core.di.Constants
import dev.bittim.encountr.core.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.GlobalContext.startKoin

class Encountr : Application() {
    private val ioAppScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Encountr)
            modules(appModule)
        }

        fetchDefinitions()
    }

    private fun fetchDefinitions() {
        val koin = GlobalContext.get()
        val sharedPreferences = koin.get<SharedPreferences>()
        val defs_url = sharedPreferences.getString(Constants.SPKEY_DEFS_URL, null)


        if (defs_url != null) {
            val definitionRepository = GlobalContext.get().get<DefinitionRepository>()
            ioAppScope.launch {
                definitionRepository.fetchDefinitions(defs_url)
            }
        }
    }
}