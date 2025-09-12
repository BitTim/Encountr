/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       LanguagePokeApiRepository.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   12.09.25, 17:05
 */

package dev.bittim.encountr.core.data.pokeapi.repo

import android.util.Log
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.domain.model.pokeapi.Language
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class LanguagePokeApiRepository : LanguageRepository {
    override suspend fun get(id: Int): Language? {
        val rawLanguage = PokeApi.getLanguage(id)
        Log.d("LanguagePokeApiRepository", "Fetched Language: $rawLanguage")

        val language = Language(
            id = rawLanguage.id,
            name = rawLanguage.name,
            localizedName = rawLanguage.names.find { it.language.name == rawLanguage.name }?.name
                ?: return null,
            countryCode = rawLanguage.iso3166
        )

        Log.d("LanguagePokeApiRepository", "Mapped Language: $language")
        return language
    }

    override suspend fun get(name: String): Language? {
        val count = PokeApi.getLanguageList(0, 0).count
        val langList = PokeApi.getLanguageList(0, count).results

        return langList.find { it.name == name }?.let { get(it.id) }
    }

    override suspend fun getAll(): List<Language> {
        Log.d("LanguagePokeApiRepository", "Fetching Languages")

        val count = PokeApi.getLanguageList(0, 1).count
        Log.d("LanguagePokeApiRepository", "Fetched Language Count: $count")

        val rawLangList = PokeApi.getLanguageList(0, count).results
        Log.d("LanguagePokeApiRepository", "Fetched ${rawLangList.size} Languages")

        val langList = coroutineScope {
            rawLangList.map {
                async(Dispatchers.IO) {
                    Log.d("LanguagePokeApiRepository", "Fetching Language: $it")
                    get(it.id)
                }
            }.awaitAll().filterNotNull()
        }

        Log.d("LanguagePokeApiRepository", "Mapped ${langList.size} Languages")
        return langList
    }
}