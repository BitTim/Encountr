/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.repo.pokemon

import dev.bittim.encountr.core.data.api.repo.Repository
import dev.bittim.encountr.core.domain.model.api.pokemon.Pokemon

interface PokemonRepository : Repository<Pokemon>