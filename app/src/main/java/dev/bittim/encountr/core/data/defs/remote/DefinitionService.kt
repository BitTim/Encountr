/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionService.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.08.25, 03:09
 */

package dev.bittim.encountr.core.data.defs.remote

import dev.bittim.encountr.core.data.defs.DefinitionsError
import dev.bittim.encountr.core.data.defs.remote.dto.DefinitionDto
import dev.bittim.encountr.core.domain.error.Result

interface DefinitionService {
    suspend fun getDefinitions(urlString: String): Result<DefinitionDto, DefinitionsError>
}