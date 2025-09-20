/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TypeSpriteMapper.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   20.09.25, 02:01
 */

package dev.bittim.encountr.core.data.pokeapi.mapping

import co.pokeapi.pokekotlin.PokeApi
import co.pokeapi.pokekotlin.model.VersionTypeSprites
import dev.bittim.encountr.core.data.pokeapi.GameError
import dev.bittim.encountr.core.domain.model.pokeapi.Version
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TypeSpriteMapper() : KoinComponent {
    private val pokeApi: PokeApi by inject()
    
    suspend fun map(
        sprites: VersionTypeSprites,
        version: Version
    ): String? {
        val versionGroup = pokeApi.getVersionGroup(version.versionGroupId)
        val generation = pokeApi.getGeneration(versionGroup.generation.id)

        return when (generation.name) {
            "generation-iii" -> when (versionGroup.name) {
                "ruby-sapphire" -> sprites.generationIii.rubySaphire.nameIcon
                "emerald" -> sprites.generationIii.emerald.nameIcon
                "firered-leafgreen" -> sprites.generationIii.fireredLeafgreen.nameIcon
                else -> throw GameError.InvalidVersionGroup(versionGroup.name)
            }

            "generation-iv" -> when (versionGroup.name) {
                "diamond-pearl" -> sprites.generationIv.diamondPearl.nameIcon
                "platinum" -> sprites.generationIv.platinum.nameIcon
                "heartgold-soulsilver" -> sprites.generationIv.heartgoldSoulsilver.nameIcon
                else -> throw GameError.InvalidVersionGroup(versionGroup.name)
            }

            "generation-v" -> when (versionGroup.name) {
                "black-white" -> sprites.generationVi.xY.nameIcon
                "black-2-white-2" -> sprites.generationVi.omegaRubyAlphaSapphire.nameIcon
                else -> throw GameError.InvalidVersionGroup(versionGroup.name)
            }

            "generation-vi" -> when (versionGroup.name) {
                "x-y" -> sprites.generationVi.xY.nameIcon
                "omega-ruby-alpha-sapphire" -> sprites.generationVi.omegaRubyAlphaSapphire.nameIcon
                else -> throw GameError.InvalidVersionGroup(versionGroup.name)
            }

            "generation-vii" -> when (versionGroup.name) {
                "sun-moon" -> sprites.generationVii.sunMoon.nameIcon
                "ultra-sun-ultra-moon" -> sprites.generationVii.ultraSunUltraMoon.nameIcon
                "lets-go-pikachu-lets-go-eevee" -> sprites.generationVii.letsGoPikachuLetsGoEevee.nameIcon
                else -> throw GameError.InvalidVersionGroup(versionGroup.name)
            }

            "generation-viii" -> when (versionGroup.name) {
                "sword-shield" -> sprites.generationViii.swordShield.nameIcon
                "brilliant-diamond-and-shining-pearl" -> sprites.generationViii.brilliantDiamondAndShiningPearl.nameIcon
                "legends-arceus" -> sprites.generationViii.legendsArceus.nameIcon
                else -> throw GameError.InvalidVersionGroup(versionGroup.name)
            }

            "generation-ix" -> when (versionGroup.name) {
                "scarlet-violet" -> sprites.generationIx.scarletViolet.nameIcon
                else -> throw GameError.InvalidVersionGroup(versionGroup.name)
            }

            else -> null
        }
    }
}