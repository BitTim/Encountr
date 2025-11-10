/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LanguageFull.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.language

import androidx.room.Embedded
import androidx.room.Relation
import dev.bittim.encountr.core.data.api.local.entity.base.TimestampedEntity
import dev.bittim.encountr.core.data.api.local.entity.base.language.LanguageDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.language.LanguageStub

data class LanguageFull(
    @Embedded val stub: LanguageStub,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    ) val detail: LanguageDetailEntity
) : TimestampedEntity by stub {
    fun toModel() = detail.toModel()
}
