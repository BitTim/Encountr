/*
 * Copyright (c) 2025 Tim Anhalt (BitTim)
 *
 * Project:    Encountr
 * License:    GPLv3
 *
 * File:       IntListConverter.kt
 * Module:     Encountr.app.main
 * Author:     Tim Anhalt (BitTim)
 * Modified:   17.11.25, 23:03
 */

package dev.bittim.encountr.core.data.common.converter

import androidx.room.TypeConverter

class IntListConverter {
    @TypeConverter
    fun fromListInt(list: List<Int>): String {
        if (list.isEmpty()) return ""
        return list.joinToString(",")
    }

    @TypeConverter
    fun toListInt(data: String): List<Int> {
        if (data.isEmpty()) return emptyList()
        return listOf(*data.split(",").map { it.toInt() }.toTypedArray())
    }
}