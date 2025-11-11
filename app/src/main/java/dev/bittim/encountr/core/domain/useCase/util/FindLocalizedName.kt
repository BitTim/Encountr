/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       FindLocalizedName.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.11.25, 01:44
 */

package dev.bittim.encountr.core.domain.useCase.util

import dev.bittim.encountr.core.di.Constants
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString

class FindLocalizedName {
    operator fun invoke(
        localizedNames: List<LocalizedString>,
        languageId: Int,
        fallbackName: String = ""
    ): String {
        return localizedNames.find { it.languageId == languageId }?.value
            ?: localizedNames.find { it.languageId == Constants.DEFAULT_LANG_ID }?.value
            ?: fallbackName
    }
}