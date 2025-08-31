/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       SelectLocaleViewModel.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   31.08.25, 16:55
 */

package dev.bittim.encountr.onboarding.ui.screens.selectLocale

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.pokeapi.pokekotlin.PokeApi
import dev.bittim.encountr.core.data.pokeapi.extension.hasSelfLocalizedName
import dev.bittim.encountr.core.domain.Paginator
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class SelectLocaleViewModel : ViewModel() {
    private val _state = MutableStateFlow(SelectLocaleState())
    val state = _state.asStateFlow()

    private val pageSize = 20
    private val paginator = Paginator(
        initialKey = 0,
        onLoadUpdated = { isLoading ->
            _state.update { it.copy(isLoading = isLoading) }
        },
        onRequest = { offset ->
            return@Paginator try {
                val response = PokeApi.getLanguageList(offset, pageSize)
                val languages = response.results.mapNotNull { handle ->
                    PokeApi.getLanguage(handle.id).takeIf { it.hasSelfLocalizedName() }
                }

                Result.success(Pair(response.count, languages))
            } catch (e: Exception) {
                coroutineContext.ensureActive()
                Result.failure(e)
            }
        },
        getNextKey = { currentKey, _ -> currentKey + pageSize },
        onError = { throwable ->
            _state.update { it.copy(error = throwable?.localizedMessage ?: throwable?.message) }
        },
        onSuccess = { item, newKey ->
            _state.update { it.copy(locales = if (it.locales == null) item.second else it.locales + item.second) }
        },
        checkFinished = { currentKey, item ->
            currentKey >= item.first
        }
    )

    init {
        loadNext()
    }

    fun loadNext() {
        viewModelScope.launch {
            paginator.next()
        }
    }
}