/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Version.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.domain.model.api.version

import dev.bittim.encountr.core.domain.model.api.Handle
import dev.bittim.encountr.core.domain.model.api.Handleable
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString

data class Version(
    val id: Int,
    val name: String,
    val localizedNames: List<LocalizedString>,
    val versionGroup: Handle<VersionGroup>,
    val imageUrl: String?,
) : Handleable