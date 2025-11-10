/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       FindLocalizedName.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.domain.useCase

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