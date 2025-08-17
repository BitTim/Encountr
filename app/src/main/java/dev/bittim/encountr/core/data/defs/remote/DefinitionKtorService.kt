/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionKtorService.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.08.25, 03:09
 */

package dev.bittim.encountr.core.data.defs.remote

import dev.bittim.encountr.core.data.defs.DefinitionsError
import dev.bittim.encountr.core.data.defs.remote.dto.DefinitionDto
import dev.bittim.encountr.core.domain.error.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.Url
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

class DefinitionKtorService(
    private val client: HttpClient,
) : DefinitionService {
    override suspend fun getDefinitions(urlString: String): Result<DefinitionDto, DefinitionsError> {
        val checkedUrl = try {
            Url(urlString)
        } catch (_: Exception) {
            coroutineContext.ensureActive()
            return Result.Err(DefinitionsError.InvalidUrl)
        }

        val response = try {
            client.get(checkedUrl)
        } catch (_: Exception) {
            coroutineContext.ensureActive()
            return Result.Err(DefinitionsError.InvalidResponse)
        }

        return try {
            Result.Ok(response.body<DefinitionDto>())
        } catch (_: Exception) {
            coroutineContext.ensureActive()
            return Result.Err(DefinitionsError.InvalidContent)
        }
    }
}