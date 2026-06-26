package com.sirko007.cryptopulse.data.local

import androidx.room.TypeConverter

/** Converts the sparkline price list to/from a comma-separated string for Room. */
class Converters {

    @TypeConverter
    fun fromList(values: List<Double>): String =
        values.joinToString(separator = ",")

    @TypeConverter
    fun toList(raw: String): List<Double> =
        if (raw.isBlank()) emptyList()
        else raw.split(",").mapNotNull { it.toDoubleOrNull() }
}
