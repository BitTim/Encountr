/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ApiDatabase.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   07.11.25, 01:13
 */

package dev.bittim.encountr.core.data.api.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.bittim.encountr.core.data.api.local.dao.base.GenerationDao
import dev.bittim.encountr.core.data.api.local.dao.base.LanguageDao
import dev.bittim.encountr.core.data.api.local.dao.base.PokedexDao
import dev.bittim.encountr.core.data.api.local.dao.base.PokemonDao
import dev.bittim.encountr.core.data.api.local.dao.base.TypeDao
import dev.bittim.encountr.core.data.api.local.dao.base.VersionDao
import dev.bittim.encountr.core.data.api.local.dao.base.VersionGroupDao
import dev.bittim.encountr.core.data.api.local.dao.junction.PokedexPokemonJunctionDao
import dev.bittim.encountr.core.data.api.local.dao.junction.PokemonTypeJunctionDao
import dev.bittim.encountr.core.data.api.local.dao.junction.VersionGroupPokedexJunctionDao
import dev.bittim.encountr.core.data.api.local.entity.base.ExpirableEntity
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationEntity
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.language.LanguageEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonSpritesEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeSpriteEntity
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionEntity
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.versionGroup.VersionGroupEntity
import dev.bittim.encountr.core.data.api.local.entity.junction.PokedexPokemonJunction
import dev.bittim.encountr.core.data.api.local.entity.junction.PokemonTypeJunction
import dev.bittim.encountr.core.data.api.local.entity.junction.VersionGroupPokedexJunction
import dev.bittim.encountr.core.data.common.converter.IntListConverter
import dev.bittim.encountr.core.domain.model.api.language.Language
import dev.bittim.encountr.core.domain.model.api.pokedex.Pokedex
import dev.bittim.encountr.core.domain.model.api.pokemon.Pokemon
import dev.bittim.encountr.core.domain.model.api.type.Type
import dev.bittim.encountr.core.domain.model.api.version.Generation
import dev.bittim.encountr.core.domain.model.api.version.Version
import dev.bittim.encountr.core.domain.model.api.version.VersionGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@TypeConverters(IntListConverter::class)
@Database(
    version = 1,
    entities = [
        GenerationEntity::class, GenerationLocalizedNameEntity::class,
        LanguageEntity::class,
        PokedexEntity::class, PokedexLocalizedNameEntity::class,
        PokemonEntity::class, PokemonLocalizedNameEntity::class, PokemonSpritesEntity::class,
        TypeEntity::class, TypeLocalizedNameEntity::class, TypeSpriteEntity::class,
        VersionEntity::class, VersionLocalizedNameEntity::class,
        VersionGroupEntity::class,

        PokedexPokemonJunction::class, PokemonTypeJunction::class, VersionGroupPokedexJunction::class,
    ]
)
abstract class ApiDatabase : RoomDatabase() {
    // region:      -- Base Dao

    abstract fun generationDao(): GenerationDao
    abstract fun languageDao(): LanguageDao
    abstract fun pokedexDao(): PokedexDao
    abstract fun pokemonDao(): PokemonDao
    abstract fun typeDao(): TypeDao
    abstract fun versionDao(): VersionDao
    abstract fun versionGroupDao(): VersionGroupDao

    // endregion:   -- Base Dao
    // region:      -- Junction Dao

    abstract fun pokedexVersionGroupJunctionDao(): VersionGroupPokedexJunctionDao
    abstract fun pokedexPokemonJunctionDao(): PokedexPokemonJunctionDao
    abstract fun pokemonTypeJunctionDao(): PokemonTypeJunctionDao

    // endregion:   -- Junction Dao
    // region:      -- Worker Helper Functions

    fun getOf(type: String?, id: Int): Flow<ExpirableEntity?> {
        return when (type) {
            Generation::class.simpleName -> generationDao().get(id)
            Language::class.simpleName -> languageDao().get(id)
            Pokedex::class.simpleName -> pokedexDao().get(id)
            Pokemon::class.simpleName -> pokemonDao().get(id)
            Type::class.simpleName -> typeDao().get(id)
            Version::class.simpleName -> versionDao().get(id)
            VersionGroup::class.simpleName -> versionGroupDao().get(id)

            else -> return flowOf(null)
        }
    }

    fun getOf(type: String?): Flow<List<ExpirableEntity>> {
        return when (type) {
            Generation::class.simpleName -> generationDao().get()
            Language::class.simpleName -> languageDao().get()
            Pokedex::class.simpleName -> pokedexDao().get()
            Pokemon::class.simpleName -> pokemonDao().get()
            Type::class.simpleName -> typeDao().get()
            Version::class.simpleName -> versionDao().get()
            VersionGroup::class.simpleName -> versionGroupDao().get()

            else -> return flowOf(emptyList())
        }
    }

    // endregion:   -- Worker Helper Functions
}