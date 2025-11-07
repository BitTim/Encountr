/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Pokemon.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.domain.model.api.pokemon

import dev.bittim.encountr.core.domain.model.api.Handle
import dev.bittim.encountr.core.domain.model.api.Handleable
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString
import dev.bittim.encountr.core.domain.model.api.type.Type

data class Pokemon(
    val id: Int,
    val entryNumbers: List<PokedexEntry>,
    val name: String,
    val localizedNames: List<LocalizedString>,
    val height: String,
    val weight: String,
    val sprites: List<PokemonSprites>,
    val types: List<Handle<Type>>,
) : Handleable
