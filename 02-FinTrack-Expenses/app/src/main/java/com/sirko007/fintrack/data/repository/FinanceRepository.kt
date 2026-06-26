package com.sirko007.fintrack.data.repository

import com.sirko007.fintrack.data.local.TransactionDao
import com.sirko007.fintrack.data.local.TransactionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Offline-first single source of truth. The whole app reads from Room through
 * this repository; there is no network layer at all.
 */
@Singleton
class FinanceRepository @Inject constructor(
    private val dao: TransactionDao
) {
    fun observeTransactions(): Flow<List<TransactionEntity>> = dao.observeAll()

    suspend fun add(transaction: TransactionEntity): Long = dao.insert(transaction)

    suspend fun remove(transaction: TransactionEntity) = dao.delete(transaction)
}
