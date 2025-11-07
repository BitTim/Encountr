/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionFileLoader.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.defs.file

import android.content.Context
import dev.bittim.encountr.R
import dev.bittim.encountr.core.data.defs.file.dto.DefinitionDto
import kotlinx.serialization.json.Json

class DefinitionFileLoader(
    private val context: Context,
) : DefinitionLoader {
    override suspend fun get(): DefinitionDto {
        val rawJson = context.resources.openRawResource(R.raw.definitions).bufferedReader()
            .use { it.readText() }

        return Json.decodeFromString<DefinitionDto>(rawJson)
    }
}