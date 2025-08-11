/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionsResponse.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.08.25, 17:37
 */

package dev.bittim.encountr.core.data.defs.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class DefinitionsResponse(
    val version: Int,
    val definitions: List<DefinitionDto>
)