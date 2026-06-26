package com.sirko007.cryptopulse.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Cached coin row. The 7-day sparkline (a list of prices) is persisted via a
 * TypeConverter — this is what makes the app work offline after the first load.
 */
@Entity(tableName = "coins")
data class CoinEntity(
    @PrimaryKey val id: String,
    val symbol: String,
    val name: String,
    val image: String?,
    val rank: Int,
    val currentPrice: Double,
    val priceChange24h: Double,
    val marketCap: Double,
    val sparkline: List<Double>
)
