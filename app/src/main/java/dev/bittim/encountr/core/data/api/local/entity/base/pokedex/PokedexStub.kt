/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokedexStub.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.11.25, 03:04
 */

package dev.bittim.encountr.core.data.api.local.entity.base.pokedex

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.data.api.local.entity.base.StubEntity

@Entity(tableName = "pokedex_stub")
data class PokedexStub(
    @PrimaryKey override val id: Int,
    override val updatedAt: Long = StubEntity.generateTimestamp()
) : StubEntity
