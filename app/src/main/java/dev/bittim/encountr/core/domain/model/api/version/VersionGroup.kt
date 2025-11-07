/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionGroup.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.domain.model.api.version

import dev.bittim.encountr.core.domain.model.api.Handle
import dev.bittim.encountr.core.domain.model.api.Handleable
import dev.bittim.encountr.core.domain.model.api.pokedex.Pokedex

data class VersionGroup(
    val id: Int,
    val name: String,
    val generation: Handle<Generation>,
    val versions: List<Handle<Version>>,
    val pokedexes: List<Handle<Pokedex>>,
) : Handleable