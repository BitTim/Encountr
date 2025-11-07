/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Language.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.domain.model.api.language

import dev.bittim.encountr.core.domain.model.api.Handleable

data class Language(
    val id: Int,
    val name: String,
    val localizedName: String,
    val countryCode: String,
) : Handleable
