/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonSpritesEntity.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local.entity.base.pokemon

import androidx.room.Entity
import androidx.room.ForeignKey
import dev.bittim.encountr.core.data.api.extension.toEntity
import dev.bittim.encountr.core.data.api.local.entity.base.ExpirableEntity
import dev.bittim.encountr.core.domain.model.api.pokemon.PokemonSpriteVariant
import dev.bittim.encountr.core.domain.model.api.pokemon.PokemonSprites

@Entity(
    tableName = "pokemon_sprites",
    primaryKeys = ["pokemonId", "pokemonSpriteVariant"],
    foreignKeys = [
        ForeignKey(
            entity = PokemonEntity::class,
            parentColumns = ["id"],
            childColumns = ["pokemonId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class PokemonSpritesEntity(
    val pokemonId: Int,
    override val expiresAt: Long,
    val pokemonSpriteVariant: PokemonSpriteVariant,
    val frontDefault: String?,
    val frontShiny: String?,
    val backDefault: String?,
    val backShiny: String?,
    val frontFemale: String?,
    val frontShinyFemale: String?,
    val backFemale: String?,
    val backShinyFemale: String?
) : ExpirableEntity {
    fun toModel(): PokemonSprites {
        return PokemonSprites(
            frontDefault = frontDefault,
            frontShiny = frontShiny,
            backDefault = backDefault,
            backShiny = backShiny,
            frontFemale = frontFemale,
            frontShinyFemale = frontShinyFemale,
            backFemale = backFemale,
            backShinyFemale = backShinyFemale,
        )
    }

    companion object {
        fun fromApi(
            pokemonId: Int,
            pokemonSprites: co.pokeapi.pokekotlin.model.PokemonSprites
        ): List<PokemonSpritesEntity> {
            return PokemonSpriteVariant.entries.map { spriteType ->
                when (spriteType) {
                    PokemonSpriteVariant.DEFAULT -> pokemonSprites.toEntity(pokemonId, spriteType)
                    PokemonSpriteVariant.DREAM_WORLD -> pokemonSprites.other.dreamWorld.toEntity(
                        pokemonId,
                        spriteType
                    )

                    PokemonSpriteVariant.HOME -> pokemonSprites.other.home.toEntity(
                        pokemonId,
                        spriteType
                    )

                    PokemonSpriteVariant.OFFICIAL_ARTWORK -> pokemonSprites.other.officialArtwork.toEntity(
                        pokemonId,
                        spriteType
                    )

                    PokemonSpriteVariant.SHOWDOWN -> pokemonSprites.other.showdown.toEntity(
                        pokemonId,
                        spriteType
                    )

                    PokemonSpriteVariant.RED_BLUE -> pokemonSprites.versions.generationI.redBlue.toEntity(
                        pokemonId,
                        spriteType
                    )

                    PokemonSpriteVariant.YELLOW -> pokemonSprites.versions.generationI.yellow.toEntity(
                        pokemonId,
                        spriteType
                    )

                    PokemonSpriteVariant.GOLD -> pokemonSprites.versions.generationIi.gold.toEntity(
                        pokemonId,
                        spriteType
                    )

                    PokemonSpriteVariant.SILVER -> pokemonSprites.versions.generationIi.silver.toEntity(
                        pokemonId,
                        spriteType
                    )

                    PokemonSpriteVariant.CRYSTAL -> pokemonSprites.versions.generationIi.crystal.toEntity(
                        pokemonId,
                        spriteType
                    )

                    PokemonSpriteVariant.RUBY_SAPPHIRE -> pokemonSprites.versions.generationIii.rubySapphire.toEntity(
                        pokemonId,
                        spriteType
                    )

                    PokemonSpriteVariant.EMERALD -> pokemonSprites.versions.generationIii.emerald.toEntity(
                        pokemonId,
                        spriteType
                    )

                    PokemonSpriteVariant.FIRERED_LEAFGREEN -> pokemonSprites.versions.generationIii.fireredLeafgreen.toEntity(
                        pokemonId,
                        spriteType
                    )

                    PokemonSpriteVariant.DIAMOND_PEARL -> pokemonSprites.versions.generationIv.diamondPearl.toEntity(
                        pokemonId,
                        spriteType
                    )

                    PokemonSpriteVariant.PLATINUM -> pokemonSprites.versions.generationIv.platinum.toEntity(
                        pokemonId,
                        spriteType
                    )

                    PokemonSpriteVariant.HEARTGOLD_SOULSILVER -> pokemonSprites.versions.generationIv.heartgoldSoulsilver.toEntity(
                        pokemonId,
                        spriteType
                    )

                    PokemonSpriteVariant.BLACK_WHITE -> pokemonSprites.versions.generationV.blackWhite.toEntity(
                        pokemonId
                    )

                    PokemonSpriteVariant.XY -> pokemonSprites.versions.generationVi.xY.toEntity(
                        pokemonId,
                        spriteType
                    )

                    PokemonSpriteVariant.OMEGA_RUBY_ALPHA_SAPPHIRE -> pokemonSprites.versions.generationVi.omegaRubyAlphaSapphire.toEntity(
                        pokemonId,
                        spriteType
                    )

                    PokemonSpriteVariant.SUN_MOON -> pokemonSprites.versions.generationVii.ultraSunUltraMoon.toEntity(
                        pokemonId,
                        spriteType
                    )

                    PokemonSpriteVariant.GEN_7_ICON -> pokemonSprites.versions.generationVii.icons.toEntity(
                        pokemonId,
                        spriteType
                    )

                    PokemonSpriteVariant.GEN_8_ICON -> pokemonSprites.versions.generationViii.icons.toEntity(
                        pokemonId,
                        spriteType
                    )
                }
            }
        }
    }
}
