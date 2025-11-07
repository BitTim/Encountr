/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Pokedex.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.domain.model.api.pokedex

import dev.bittim.encountr.core.domain.model.api.Handle
import dev.bittim.encountr.core.domain.model.api.Handleable
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString
import dev.bittim.encountr.core.domain.model.api.pokemon.Pokemon

data class Pokedex(
    val id: Int,
    val name: String,
    val isMainSeries: Boolean,
    val localizedNames: List<LocalizedString>,
    val pokemon: List<Handle<Pokemon>>,
) : Handleable
