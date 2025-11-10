/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TypeDetailEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.entity.base.type

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import dev.bittim.encountr.core.domain.model.api.language.LocalizedString
import dev.bittim.encountr.core.domain.model.api.type.Type
import dev.bittim.encountr.core.domain.model.api.type.TypeSprite

@Entity(
    tableName = "type_detail",
    foreignKeys = [
        ForeignKey(
            entity = TypeStub::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class TypeDetailEntity(
    @PrimaryKey val id: Int,
    val name: String,

    val doubleDamageFrom: List<Int>,
    val halfDamageFrom: List<Int>,
    val noDamageFrom: List<Int>,
    val doubleDamageTo: List<Int>,
    val halfDamageTo: List<Int>,
    val noDamageTo: List<Int>,
) {
    fun toModel(
        localizedNames: List<LocalizedString>,
        typeSprites: List<TypeSprite>
    ): Type {
        return Type(
            id = id,
            name = name,
            localizedNames = localizedNames,
            sprites = typeSprites,

            doubleDamageFrom = doubleDamageFrom,
            halfDamageFrom = halfDamageFrom,
            noDamageFrom = noDamageFrom,
            doubleDamageTo = doubleDamageTo,
            halfDamageTo = halfDamageTo,
            noDamageTo = noDamageTo,
        )
    }

    companion object {
        fun fromApi(type: co.pokeapi.pokekotlin.model.Type): TypeDetailEntity {
            return TypeDetailEntity(
                id = type.id,
                name = type.name,

                doubleDamageFrom = type.damageRelations.doubleDamageFrom.map { it.id },
                halfDamageFrom = type.damageRelations.halfDamageFrom.map { it.id },
                noDamageFrom = type.damageRelations.noDamageFrom.map { it.id },
                doubleDamageTo = type.damageRelations.doubleDamageTo.map { it.id },
                halfDamageTo = type.damageRelations.halfDamageTo.map { it.id },
                noDamageTo = type.damageRelations.noDamageTo.map { it.id },
            )
        }
    }
}
