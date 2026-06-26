package com.sirko007.cryptopulse.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirko007.cryptopulse.data.local.CoinEntity
import com.sirko007.cryptopulse.data.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ListUiState(
    val isRefreshing: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    // Coins are observed from Room, so the list shows cached data instantly (offline).
    val coins: StateFlow<List<CoinEntity>> = repository.observeCoins()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _uiState = MutableStateFlow(ListUiState())
    val uiState: StateFlow<ListUiState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        if (_uiState.value.isRefreshing) return
        _uiState.update { it.copy(isRefreshing = true, error = null) }
        viewModelScope.launch {
            val result = repository.refresh()
            _uiState.update {
                it.copy(
                    isRefreshing = false,
                    error = result.exceptionOrNull()?.let { e ->
                        "No se pudieron actualizar los precios: ${e.message ?: "error de red"}"
                    }
                )
            }
        }
    }

    fun dismissError() = _uiState.update { it.copy(error = null) }
}
