/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       ApiDatabase.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.11.25, 03:00
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
import dev.bittim.encountr.core.data.api.local.entity.base.CombinedEntity
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.generation.GenerationStub
import dev.bittim.encountr.core.data.api.local.entity.base.language.LanguageDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.language.LanguageStub
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokedex.PokedexStub
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonSpritesEntity
import dev.bittim.encountr.core.data.api.local.entity.base.pokemon.PokemonStub
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeSpriteEntity
import dev.bittim.encountr.core.data.api.local.entity.base.type.TypeStub
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionLocalizedNameEntity
import dev.bittim.encountr.core.data.api.local.entity.base.version.VersionStub
import dev.bittim.encountr.core.data.api.local.entity.base.versionGroup.VersionGroupDetailEntity
import dev.bittim.encountr.core.data.api.local.entity.base.versionGroup.VersionGroupStub
import dev.bittim.encountr.core.data.api.local.entity.junction.PokedexPokemonJunction
import dev.bittim.encountr.core.data.api.local.entity.junction.PokemonTypeJunction
import dev.bittim.encountr.core.data.api.local.entity.junction.VersionGroupPokedexJunction
import dev.bittim.encountr.core.data.common.converter.IntListConverter
import dev.bittim.encountr.core.domain.model.api.generation.Generation
import dev.bittim.encountr.core.domain.model.api.language.Language
import dev.bittim.encountr.core.domain.model.api.pokedex.Pokedex
import dev.bittim.encountr.core.domain.model.api.pokemon.Pokemon
import dev.bittim.encountr.core.domain.model.api.type.Type
import dev.bittim.encountr.core.domain.model.api.version.Version
import dev.bittim.encountr.core.domain.model.api.versionGroup.VersionGroup
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@TypeConverters(IntListConverter::class)
@Database(
    version = 1,
    entities = [
        GenerationStub::class, GenerationDetailEntity::class, GenerationLocalizedNameEntity::class,
        LanguageStub::class, LanguageDetailEntity::class,
        PokedexStub::class, PokedexDetailEntity::class, PokedexLocalizedNameEntity::class,
        PokemonStub::class, PokemonDetailEntity::class, PokemonLocalizedNameEntity::class, PokemonSpritesEntity::class,
        TypeStub::class, TypeDetailEntity::class, TypeLocalizedNameEntity::class, TypeSpriteEntity::class,
        VersionStub::class, VersionDetailEntity::class, VersionLocalizedNameEntity::class,
        VersionGroupStub::class, VersionGroupDetailEntity::class,

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

    abstract fun versionGroupPokedexJunctionDao(): VersionGroupPokedexJunctionDao
    abstract fun pokedexPokemonJunctionDao(): PokedexPokemonJunctionDao
    abstract fun pokemonTypeJunctionDao(): PokemonTypeJunctionDao

    // endregion:   -- Junction Dao
    // region:      -- Worker Helper Functions

    fun getOf(type: String?, id: Int): Flow<CombinedEntity?> {
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

    fun getIdsOf(type: String?): Flow<List<Int>> {
        return when (type) {
            Generation::class.simpleName -> generationDao().getIds()
            Language::class.simpleName -> languageDao().getIds()
            Pokedex::class.simpleName -> pokedexDao().getIds()
            Pokemon::class.simpleName -> pokemonDao().getIds()
            Type::class.simpleName -> typeDao().getIds()
            Version::class.simpleName -> versionDao().getIds()
            VersionGroup::class.simpleName -> versionGroupDao().getIds()

            else -> return flowOf(emptyList())
        }
    }

    // endregion:   -- Worker Helper Functions
}