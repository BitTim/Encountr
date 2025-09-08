/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SpriteMapping.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   08.09.25, 02:16
 */

package dev.bittim.encountr.core.data.pokeapi.mapping

import android.util.Log
import co.pokeapi.pokekotlin.PokeApi
import co.pokeapi.pokekotlin.model.PokemonSprites
import co.pokeapi.pokekotlin.model.Version
import dev.bittim.encountr.core.data.pokeapi.GameError
import dev.bittim.encountr.core.data.pokeapi.extension.toMappedSprites
import dev.bittim.encountr.core.domain.error.Result
import dev.bittim.encountr.core.domain.model.pokeapi.MappedPokemonSprites

suspend fun mapPokemonSpriteVersion(
    sprites: PokemonSprites,
    version: Version
): Result<MappedPokemonSprites, GameError> {
    val versionGroup = PokeApi.getVersionGroup(version.versionGroup.id)
    val generation = PokeApi.getGeneration(versionGroup.generation.id)
    Log.d("PokemonListViewModel", "Generation: $generation")
    Log.d("PokemonListViewModel", "Version group: $versionGroup")
    Log.d("PokemonListViewModel", "Version: $version")

    return when (generation.name) {
        "generation-i" -> when (versionGroup.name) {
            "red-blue", "red-green-japan", "blue-japan" -> Result.Ok(sprites.versions.generationI.redBlue.toMappedSprites())
            "yellow" -> Result.Ok(sprites.versions.generationI.yellow.toMappedSprites())
            else -> Result.Err(GameError.InvalidVersionGroup(versionGroup.name))
        }

        "generation-ii" -> when (version.name) {
            "gold" -> Result.Ok(sprites.versions.generationIi.gold.toMappedSprites())
            "silver" -> Result.Ok(sprites.versions.generationIi.silver.toMappedSprites())
            "crystal" -> Result.Ok(sprites.versions.generationIi.crystal.toMappedSprites())
            else -> Result.Err(GameError.InvalidVersion(version.name))
        }

        "generation-iii" -> when (versionGroup.name) {
            "ruby-sapphire" -> Result.Ok(sprites.versions.generationIii.rubySapphire.toMappedSprites())
            "emerald" -> Result.Ok(sprites.versions.generationIii.emerald.toMappedSprites())
            "firered-leafgreen" -> Result.Ok(sprites.versions.generationIii.fireredLeafgreen.toMappedSprites())
            else -> Result.Err(GameError.InvalidVersionGroup(versionGroup.name))
        }

        "generation-iv" -> when (versionGroup.name) {
            "diamond-pearl" -> Result.Ok(sprites.versions.generationIv.diamondPearl.toMappedSprites())
            "platinum" -> Result.Ok(sprites.versions.generationIv.platinum.toMappedSprites())
            "heartgold-soulsilver" -> Result.Ok(sprites.versions.generationIv.heartgoldSoulsilver.toMappedSprites())
            else -> Result.Err(GameError.InvalidVersionGroup(versionGroup.name))
        }

        "generation-v" -> Result.Ok(sprites.versions.generationV.blackWhite.toMappedSprites())
        "generation-vi" -> when (versionGroup.name) {
            "x-y" -> Result.Ok(sprites.versions.generationVi.xY.toMappedSprites())
            "omega-ruby-alpha-sapphire" -> Result.Ok(sprites.versions.generationVi.omegaRubyAlphaSapphire.toMappedSprites())
            else -> Result.Err(GameError.InvalidVersionGroup(versionGroup.name))
        }

        "generation-vii" -> Result.Ok(sprites.versions.generationVii.ultraSunUltraMoon.toMappedSprites())
        else -> Result.Ok(sprites.toMappedSprites())
    }
}