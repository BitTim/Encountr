/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LanguageFull.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.11.25, 03:06
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.language

import androidx.room.Embedded
import androidx.room.Relation
import dev.bittim.encountr.core.data.api.local.entity.base.CombinedEntity
import dev.bittim.encountr.core.data.api.local.entity.base.language.LanguageDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.language.LanguageStub

data class LanguageFull(
    @Embedded override val stub: LanguageStub,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    ) override val detail: LanguageDetailEntity?
) : CombinedEntity {
    fun toModel() = detail?.toModel()
}
