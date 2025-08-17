/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       Paginator.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   16.08.25, 18:23
 */

package dev.bittim.encountr.core.domain

class Paginator<Key, Item>(
    private val initialKey: Key,
    private val onLoadUpdated: (Boolean) -> Unit,
    private val onRequest: suspend (nextKey: Key) -> Result<Item>,
    private val getNextKey: suspend (currentKey: Key, item: Item) -> Key,
    private val onError: suspend (Throwable?) -> Unit,
    private val onSuccess: suspend (item: Item, newKey: Key) -> Unit,
    private val checkFinished: suspend (currentKey: Key, item: Item) -> Boolean,
) {
    private var currentKey = initialKey
    private var isMakingRequest = false
    private var isFinished = false

    suspend fun next() {
        if (isMakingRequest || isFinished) {
            return
        }

        isMakingRequest = true
        onLoadUpdated(true)

        val result = onRequest(currentKey)
        isMakingRequest = false

        val item = result.getOrElse {
            onError(it)
            onLoadUpdated(false)
            return
        }

        currentKey = getNextKey(currentKey, item)
        onSuccess(item, currentKey)
        onLoadUpdated(false)
        isFinished = checkFinished(currentKey, item)
    }

    fun reset() {
        currentKey = initialKey
        isFinished = false
    }
}