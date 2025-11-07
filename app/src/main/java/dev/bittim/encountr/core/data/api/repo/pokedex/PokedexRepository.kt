/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.repo.pokedex

import dev.bittim.encountr.core.data.api.repo.Repository
import dev.bittim.encountr.core.domain.model.api.pokedex.Pokedex

interface PokedexRepository : Repository<Pokedex>