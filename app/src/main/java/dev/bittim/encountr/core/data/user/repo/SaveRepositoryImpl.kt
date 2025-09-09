/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SaveRepositoryImpl.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   10.09.25, 00:07
 */

package dev.bittim.encountr.core.data.user.repo

import dev.bittim.encountr.core.data.user.local.UserDatabase
import dev.bittim.encountr.core.data.user.local.entity.SaveEntity
import dev.bittim.encountr.core.domain.model.user.Save
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class SaveRepositoryImpl(
    private val userDatabase: UserDatabase
) : SaveRepository {
    // region:      -- Create

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun create(
        name: String,
        gameId: Int
    ): Save {
        val save = Save(
            id = Uuid.random(),
            name = name,
            version = gameId
        )

        userDatabase.saveDao.insert(SaveEntity(save))
        return save
    }

    // endregion:   -- Create
    // region:      -- Read

    @OptIn(ExperimentalUuidApi::class)
    override fun get(id: Uuid): Flow<Save?> {
        return userDatabase.saveDao.get(id.toString()).map { it?.toModel() }
    }

    override fun getAll(): Flow<List<Save>> {
        return userDatabase.saveDao.getAll().map { it.map { saveEntity -> saveEntity.toModel() } }
    }

    // endregion:   -- Read
    // region:      -- Update

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun update(id: Uuid, name: String, gameId: Int) {
        userDatabase.saveDao.update(id.toString(), name, gameId)
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateName(id: Uuid, name: String) {
        userDatabase.saveDao.updateName(id.toString(), name)
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateGame(
        id: Uuid,
        gameId: Int
    ) {
        userDatabase.saveDao.updateGame(id.toString(), gameId)
    }

    // endregion:   -- Update
    // region:      -- Delete

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun delete(id: Uuid) {
        userDatabase.saveDao.delete(id.toString())
    }

    override suspend fun deleteAll() {
        userDatabase.saveDao.deleteAll()
    }

    // endregion:   -- Delete
}