/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TypeSpriteEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.11.25, 23:36
 */

package dev.bittim.encountr.core.data.api.local.entity.base.type

import androidx.room.Entity
import androidx.room.ForeignKey
import co.pokeapi.pokekotlin.model.VersionTypeSprites
import dev.bittim.encountr.core.data.api.extension.toEntity
import dev.bittim.encountr.core.domain.model.api.type.TypeSprite
import dev.bittim.encountr.core.domain.model.api.type.TypeSpriteVariant

@Entity(
    tableName = "type_sprites",
    primaryKeys = ["typeId", "typeSpriteVariant"],
    foreignKeys = [
        ForeignKey(
            entity = TypeStub::class,
            parentColumns = ["id"],
            childColumns = ["typeId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class TypeSpriteEntity(
    val typeId: Int,
    val typeSpriteVariant: TypeSpriteVariant,
    val imageUrl: String?,
) {
    fun toModel(): TypeSprite {
        return TypeSprite(
            typeSpriteVariant = typeSpriteVariant,
            imageUrl = imageUrl
        )
    }

    companion object {
        fun fromApi(typeId: Int, typeSprites: VersionTypeSprites): List<TypeSpriteEntity> {
            return TypeSpriteVariant.entries.map { variant ->
                when (variant) {
                    TypeSpriteVariant.RUBY_SAPPHIRE -> typeSprites.generationIii.rubySaphire.toEntity(
                        typeId,
                        variant
                    )

                    TypeSpriteVariant.EMERALD -> typeSprites.generationIii.emerald.toEntity(
                        typeId,
                        variant
                    )

                    TypeSpriteVariant.FIRERED_LEAFGREEN -> typeSprites.generationIii.fireredLeafgreen.toEntity(
                        typeId,
                        variant
                    )

                    TypeSpriteVariant.COLOSSEUM -> typeSprites.generationIii.colosseum.toEntity(
                        typeId,
                        variant
                    )

                    TypeSpriteVariant.XD -> typeSprites.generationIii.xd.toEntity(
                        typeId,
                        variant
                    )

                    TypeSpriteVariant.DIAMOND_PEARL -> typeSprites.generationIv.diamondPearl.toEntity(
                        typeId,
                        variant
                    )

                    TypeSpriteVariant.PLATINUM -> typeSprites.generationIv.platinum.toEntity(
                        typeId,
                        variant
                    )

                    TypeSpriteVariant.HEARTGOLD_SOULSILVER -> typeSprites.generationIv.heartgoldSoulsilver.toEntity(
                        typeId,
                        variant
                    )

                    TypeSpriteVariant.BLACK_WHITE -> typeSprites.generationV.blackWhite.toEntity(
                        typeId,
                        variant
                    )

                    TypeSpriteVariant.BLACK2_WHITE2 -> typeSprites.generationV.black2White2.toEntity(
                        typeId,
                        variant
                    )

                    TypeSpriteVariant.XY -> typeSprites.generationVi.xY.toEntity(
                        typeId,
                        variant
                    )

                    TypeSpriteVariant.OMEGA_RUBY_ALPHA_SAPPHIRE -> typeSprites.generationVi.omegaRubyAlphaSapphire.toEntity(
                        typeId,
                        variant
                    )

                    TypeSpriteVariant.SUN_MOON -> typeSprites.generationVii.sunMoon.toEntity(
                        typeId,
                        variant
                    )

                    TypeSpriteVariant.ULTRA_SUN_ULTRA_MOON -> typeSprites.generationVii.ultraSunUltraMoon.toEntity(
                        typeId,
                        variant
                    )

                    TypeSpriteVariant.LETS_GO_PIKACHU_LETS_GO_EEVEE -> typeSprites.generationVii.letsGoPikachuLetsGoEevee.toEntity(
                        typeId,
                        variant
                    )

                    TypeSpriteVariant.SWORD_SHIELD -> typeSprites.generationViii.swordShield.toEntity(
                        typeId,
                        variant
                    )

                    TypeSpriteVariant.BRILLIANT_DIAMOND_SHINING_PEARL -> typeSprites.generationViii.brilliantDiamondAndShiningPearl.toEntity(
                        typeId,
                        variant
                    )

                    TypeSpriteVariant.LEGENDS_ARCEUS -> typeSprites.generationViii.legendsArceus.toEntity(
                        typeId,
                        variant
                    )

                    TypeSpriteVariant.SCARLET_VIOLET -> typeSprites.generationIx.scarletViolet.toEntity(
                        typeId,
                        variant
                    )
                }
            }
        }
    }
}
