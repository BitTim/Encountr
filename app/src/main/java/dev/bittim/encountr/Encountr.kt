/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Encountr.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.08.25, 02:32
 */

package dev.bittim.encountr

import android.app.Application
import dev.bittim.encountr.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class Encountr : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Encountr)
            modules(appModule)
        }
    }
}