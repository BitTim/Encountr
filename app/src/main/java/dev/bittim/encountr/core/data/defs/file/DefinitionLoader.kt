/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionLoader.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.defs.file

import dev.bittim.encountr.core.data.defs.file.dto.DefinitionDto

interface DefinitionLoader {
    suspend fun get(): DefinitionDto
}