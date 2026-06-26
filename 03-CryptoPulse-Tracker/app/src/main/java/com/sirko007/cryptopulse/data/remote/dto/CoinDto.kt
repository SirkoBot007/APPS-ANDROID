package com.sirko007.cryptopulse.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Maps a single entry of CoinGecko's /coins/markets response.
 * Docs: https://docs.coingecko.com/reference/coins-markets
 */
@JsonClass(generateAdapter = true)
data class CoinDto(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String?,
    @Json(name = "market_cap_rank") val rank: Int?,
    @Json(name = "current_price") val currentPrice: Double?,
    @Json(name = "price_change_percentage_24h") val priceChange24h: Double?,
    @Json(name = "market_cap") val marketCap: Double?,
    @Json(name = "sparkline_in_7d") val sparkline: SparklineDto?
)

@JsonClass(generateAdapter = true)
data class SparklineDto(
    val price: List<Double> = emptyList()
)
