/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       PokemonSpriteMapper.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   20.09.25, 02:03
 */

package dev.bittim.encountr.core.data.pokeapi.mapping

import co.pokeapi.pokekotlin.PokeApi
import co.pokeapi.pokekotlin.model.PokemonSprites
import dev.bittim.encountr.core.data.pokeapi.GameError
import dev.bittim.encountr.core.data.pokeapi.extension.toMappedSprites
import dev.bittim.encountr.core.domain.model.pokeapi.MappedPokemonSprites
import dev.bittim.encountr.core.domain.model.pokeapi.Version
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PokemonSpriteMapper() : KoinComponent {
    private val pokeApi: PokeApi by inject()

    suspend fun map(
        sprites: PokemonSprites,
        version: Version
    ): MappedPokemonSprites {
        val versionGroup = pokeApi.getVersionGroup(version.versionGroupId)
        val generation = pokeApi.getGeneration(versionGroup.generation.id)

        return when (generation.name) {
            "generation-i" -> when (versionGroup.name) {
                "red-blue", "red-green-japan", "blue-japan" -> sprites.versions.generationI.redBlue.toMappedSprites()
                "yellow" -> sprites.versions.generationI.yellow.toMappedSprites()
                else -> throw GameError.InvalidVersionGroup(versionGroup.name)
            }

            "generation-ii" -> when (version.name) {
                "gold" -> sprites.versions.generationIi.gold.toMappedSprites()
                "silver" -> sprites.versions.generationIi.silver.toMappedSprites()
                "crystal" -> sprites.versions.generationIi.crystal.toMappedSprites()
                else -> throw GameError.InvalidVersion(version.name)
            }

            "generation-iii" -> when (versionGroup.name) {
                "ruby-sapphire" -> sprites.versions.generationIii.rubySapphire.toMappedSprites()
                "emerald" -> sprites.versions.generationIii.emerald.toMappedSprites()
                "firered-leafgreen" -> sprites.versions.generationIii.fireredLeafgreen.toMappedSprites()
                else -> throw GameError.InvalidVersionGroup(versionGroup.name)
            }

            "generation-iv" -> when (versionGroup.name) {
                "diamond-pearl" -> sprites.versions.generationIv.diamondPearl.toMappedSprites()
                "platinum" -> sprites.versions.generationIv.platinum.toMappedSprites()
                "heartgold-soulsilver" -> sprites.versions.generationIv.heartgoldSoulsilver.toMappedSprites()
                else -> throw GameError.InvalidVersionGroup(versionGroup.name)
            }

            "generation-v" -> sprites.versions.generationV.blackWhite.toMappedSprites()
            "generation-vi" -> when (versionGroup.name) {
                "x-y" -> sprites.versions.generationVi.xY.toMappedSprites()
                "omega-ruby-alpha-sapphire" -> sprites.versions.generationVi.omegaRubyAlphaSapphire.toMappedSprites()
                else -> throw GameError.InvalidVersionGroup(versionGroup.name)
            }

            "generation-vii" -> sprites.versions.generationVii.ultraSunUltraMoon.toMappedSprites()
            else -> sprites.toMappedSprites()
        }
    }
}