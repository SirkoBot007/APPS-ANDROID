package com.sirko007.fintrack.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirko007.fintrack.data.local.TransactionEntity
import com.sirko007.fintrack.data.repository.FinanceRepository
import com.sirko007.fintrack.domain.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddUiState(
    val title: String = "",
    val amount: String = "",
    val isExpense: Boolean = true,
    val category: Category = Category.FOOD,
    val error: String? = null,
    val saved: Boolean = false
)

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val repository: FinanceRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AddUiState())
    val state: StateFlow<AddUiState> = _state.asStateFlow()

    fun onTitleChange(v: String) = _state.update { it.copy(title = v) }
    fun onAmountChange(v: String) = _state.update { it.copy(amount = v.filter { c -> c.isDigit() || c == '.' }) }
    fun onTypeChange(isExpense: Boolean) = _state.update { it.copy(isExpense = isExpense) }
    fun onCategoryChange(c: Category) = _state.update { it.copy(category = c) }
    fun dismissError() = _state.update { it.copy(error = null) }

    fun save() {
        val s = _state.value
        val amount = s.amount.toDoubleOrNull()
        when {
            s.title.isBlank() -> _state.update { it.copy(error = "Escribe un concepto.") }
            amount == null || amount <= 0.0 -> _state.update { it.copy(error = "Escribe un importe válido.") }
            else -> viewModelScope.launch {
                repository.add(
                    TransactionEntity(
                        title = s.title.trim(),
                        amount = amount,
                        isExpense = s.isExpense,
                        category = s.category.name
                    )
                )
                _state.update { it.copy(saved = true) }
            }
        }
    }
}
