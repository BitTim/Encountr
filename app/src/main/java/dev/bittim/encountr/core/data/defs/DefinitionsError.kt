/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       DefinitionsError.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   11.08.25, 17:37
 */

package dev.bittim.encountr.core.data.defs

import dev.bittim.encountr.core.domain.error.Error

interface DefinitionsError : Error {
    object InvalidUrl : DefinitionsError
    object InvalidResponse : DefinitionsError
    object InvalidContent : DefinitionsError
    object Cache : DefinitionsError
}