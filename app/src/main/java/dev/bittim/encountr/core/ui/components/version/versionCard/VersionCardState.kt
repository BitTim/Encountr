/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionCardState.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   09.11.25, 01:10
 */

package dev.bittim.encountr.core.ui.components.version.versionCard

import dev.bittim.encountr.core.domain.model.api.Handle
import dev.bittim.encountr.core.domain.model.api.language.Language
import dev.bittim.encountr.core.domain.model.api.version.Version

data class VersionCardState(
    val name: String,
    val generation: String,
    val imageUrl: String?
) {
    constructor(version: Version, language: Handle<Language>) : this(
        name = version.localizedNames.find { it.language.id == language.id }?.value
            ?: version.name,
        generation = "Placeholder",
        imageUrl = version.imageUrl
    )
}