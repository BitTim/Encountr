/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ObserveLanguageCardState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.domain.useCase.ui

import dev.bittim.encountr.core.data.api.repo.language.LanguageRepository
import dev.bittim.encountr.core.ui.components.language.languageCard.LanguageCardState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveLanguageCardState(
    private val languageRepository: LanguageRepository
) {
    operator fun invoke(languageId: Int): Flow<LanguageCardState?> {
        return languageRepository.get(languageId).map { language ->
            if (language == null) return@map null

            LanguageCardState(
                name = language.localizedName,
                countryCode = language.countryCode
            )
        }
    }
}