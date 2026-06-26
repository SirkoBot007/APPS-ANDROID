package com.sirko007.cryptopulse.data.repository

import com.sirko007.cryptopulse.data.local.CoinDao
import com.sirko007.cryptopulse.data.local.CoinEntity
import com.sirko007.cryptopulse.data.remote.CoinGeckoApi
import com.sirko007.cryptopulse.data.remote.dto.CoinDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Network-bound repository: the UI always observes Room (offline cache), while
 * [refresh] fetches fresh prices from CoinGecko and writes them back into Room.
 * Single source of truth = the database.
 */
@Singleton
class CryptoRepository @Inject constructor(
    private val api: CoinGeckoApi,
    private val dao: CoinDao
) {
    fun observeCoins(): Flow<List<CoinEntity>> = dao.observeAll()

    fun observeCoin(id: String): Flow<CoinEntity?> = dao.observeById(id)

    /** Pulls fresh data from the network and caches it. Returns success/failure. */
    suspend fun refresh(): Result<Unit> = runCatching {
        val coins = api.getMarkets().map { it.toEntity() }
        dao.upsertAll(coins)
    }

    private fun CoinDto.toEntity() = CoinEntity(
        id = id,
        symbol = symbol.uppercase(),
        name = name,
        image = image,
        rank = rank ?: Int.MAX_VALUE,
        currentPrice = currentPrice ?: 0.0,
        priceChange24h = priceChange24h ?: 0.0,
        marketCap = marketCap ?: 0.0,
        sparkline = sparkline?.price ?: emptyList()
    )
}
