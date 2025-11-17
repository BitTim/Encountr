/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ObserveLocalizedName.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 02:31
 */

package dev.bittim.encountr.core.domain.useCase.util

import dev.bittim.encountr.core.di.Constants
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString
import dev.bittim.encountr.core.domain.useCase.config.ObserveCurrentLanguageId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveLocalizedName(
    private val observeCurrentLanguageId: ObserveCurrentLanguageId,
) {
    operator fun invoke(
        localizedNames: List<LocalizedString>?,
        fallbackName: String? = ""
    ): Flow<String> {
        return observeCurrentLanguageId().map { languageId ->
            localizedNames?.find { it.languageId == languageId }?.value
                ?: localizedNames?.find { it.languageId == Constants.DEFAULT_LANG_ID }?.value
                ?: fallbackName ?: ""
        }
    }
}