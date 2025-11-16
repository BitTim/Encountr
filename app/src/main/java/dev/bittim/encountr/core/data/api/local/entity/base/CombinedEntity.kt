/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       CombinedEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.11.25, 03:01
 */

package dev.bittim.encountr.core.data.api.local.entity.base

interface CombinedEntity {
    val stub: StubEntity
    val detail: DetailEntity?
}