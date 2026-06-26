package com.sirko007.fintrack.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A single money movement. [amount] is always stored as a positive number;
 * [isExpense] decides the sign when computing the balance.
 */
@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val amount: Double,
    val isExpense: Boolean,
    val category: String,
    val timestamp: Long = System.currentTimeMillis()
)
