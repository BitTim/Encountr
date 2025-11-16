/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       VersionEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.11.25, 03:07
 */

package dev.bittim.encountr.core.data.api.local.entity.reltaion.version

import androidx.room.Embedded
import androidx.room.Relation
import dev.bittim.encountr.core.data.api.local.entity.base.CombinedEntity
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionStub
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString

data class VersionEntity(
    @Embedded override val stub: VersionStub,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    ) override val detail: VersionDetailEntity?
) : CombinedEntity {
    fun toModel(localizedNames: List<LocalizedString>) = detail?.toModel(
        versionGroupId = stub.versionGroupId,
        localizedNames = localizedNames
    )
}
