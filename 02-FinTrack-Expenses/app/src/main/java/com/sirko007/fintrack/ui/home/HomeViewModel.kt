package com.sirko007.fintrack.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirko007.fintrack.data.local.TransactionEntity
import com.sirko007.fintrack.data.repository.FinanceRepository
import com.sirko007.fintrack.domain.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/** One arc of the donut: a category, its total spend, and its share of expenses. */
data class CategorySlice(
    val category: Category,
    val total: Double,
    val fraction: Float
)

data class HomeUiState(
    val balance: Double = 0.0,
    val income: Double = 0.0,
    val expense: Double = 0.0,
    val slices: List<CategorySlice> = emptyList(),
    val transactions: List<TransactionEntity> = emptyList()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FinanceRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = repository.observeTransactions()
        .map { transactions -> transactions.toUiState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState()
        )

    fun delete(transaction: TransactionEntity) = viewModelScope.launch {
        repository.remove(transaction)
    }

    /** Pure aggregation: balance, totals, and expense breakdown by category. */
    private fun List<TransactionEntity>.toUiState(): HomeUiState {
        val income = filter { !it.isExpense }.sumOf { it.amount }
        val expense = filter { it.isExpense }.sumOf { it.amount }

        val slices = filter { it.isExpense }
            .groupBy { Category.fromName(it.category) }
            .map { (category, items) -> category to items.sumOf { it.amount } }
            .sortedByDescending { it.second }
            .map { (category, total) ->
                CategorySlice(
                    category = category,
                    total = total,
                    fraction = if (expense > 0) (total / expense).toFloat() else 0f
                )
            }

        return HomeUiState(
            balance = income - expense,
            income = income,
            expense = expense,
            slices = slices,
            transactions = this
        )
    }
}
