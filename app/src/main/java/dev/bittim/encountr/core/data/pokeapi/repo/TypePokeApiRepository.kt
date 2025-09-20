/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       TypePokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   20.09.25, 02:03
 */

package dev.bittim.encountr.core.data.pokeapi.repo

import android.util.Log
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.domain.model.pokeapi.DamageRelations
import dev.bittim.encountr.core.domain.model.pokeapi.LocalizedString
import dev.bittim.encountr.core.domain.model.pokeapi.Type

class TypePokeApiRepository(
    private val pokeApi: PokeApi
) : TypeRepository {
    override suspend fun get(id: Int): Type? {
        val rawType = pokeApi.getType(id)
        Log.d("TypePokeApiRepository", "Raw type: $rawType")

        val type = Type(
            id = rawType.id,
            name = rawType.name,
            localizedNames = rawType.names.map {
                LocalizedString(languageName = it.language.name, value = it.name)
            },
            damageRelations = DamageRelations(
                doubleFrom = rawType.damageRelations.doubleDamageFrom.map { it.id },
                doubleTo = rawType.damageRelations.doubleDamageTo.map { it.id },
                halfFrom = rawType.damageRelations.halfDamageFrom.map { it.id },
                halfTo = rawType.damageRelations.halfDamageTo.map { it.id },
                noFrom = rawType.damageRelations.noDamageFrom.map { it.id },
                noTo = rawType.damageRelations.noDamageTo.map { it.id }
            ),
            sprites = rawType.sprites
        )

        Log.d("TypePokeApiRepository", "Type: $type")
        return type
    }
}