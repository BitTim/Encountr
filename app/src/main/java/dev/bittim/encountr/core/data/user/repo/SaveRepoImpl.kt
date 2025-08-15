/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SaveRepoImpl.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   15.08.25, 10:52
 */

package dev.bittim.encountr.core.data.user.repo

import co.pokeapi.pokekotlin.model.Version
import dev.bittim.encountr.core.data.user.local.UserDatabase
import dev.bittim.encountr.core.data.user.local.entity.SaveEntity
import dev.bittim.encountr.core.domain.model.user.Save
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class SaveRepoImpl(
    private val userDatabase: UserDatabase
) : SaveRepo {
    // region:      -- Create

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun create(
        name: String,
        game: Version
    ): Save {
        val save = Save(
            id = Uuid.random(),
            name = name,
            game = game.id
        )

        userDatabase.saveDao.insert(SaveEntity(save))
        return save
    }

    // endregion:   -- Create
    // region:      -- Read

    @OptIn(ExperimentalUuidApi::class)
    override fun get(id: Uuid): Flow<Save?> {
        return userDatabase.saveDao.get(id).map { it?.toModel() }
    }

    override fun getAll(): Flow<List<Save>> {
        return userDatabase.saveDao.getAll().map { it.map { saveEntity -> saveEntity.toModel() } }
    }

    // endregion:   -- Read
    // region:      -- Update

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun update(id: Uuid, name: String, game: Version) {
        userDatabase.saveDao.update(id, name, game.id)
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateName(id: Uuid, name: String) {
        userDatabase.saveDao.updateName(id, name)
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateGame(
        id: Uuid,
        game: Version
    ) {
        userDatabase.saveDao.updateGame(id, game.id)
    }

    // endregion:   -- Update
    // region:      -- Delete

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun delete(id: Uuid) {
        userDatabase.saveDao.delete(id)
    }

    override suspend fun deleteAll() {
        userDatabase.saveDao.deleteAll()
    }

    // endregion:   -- Delete
}