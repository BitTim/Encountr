/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TypeEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.11.25, 03:07
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.type

import androidx.room.Embedded
import androidx.room.Relation
import dev.bittim.encountr.core.data.api.local.entity.base.CombinedEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeStub
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString
import dev.bittim.encountr.core.domain.model.api.type.TypeSprite

data class TypeEntity(
    @Embedded override val stub: TypeStub,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    ) override val detail: TypeDetailEntity?
) : CombinedEntity {
    fun toModel(
        localizedNames: List<LocalizedString>,
        typeSprites: List<TypeSprite>
    ) = detail?.toModel(
        localizedNames = localizedNames,
        typeSprites = typeSprites
    )
}
