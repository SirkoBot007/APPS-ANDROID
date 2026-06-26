package com.sirko007.cryptopulse.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sirko007.cryptopulse.data.local.CoinEntity
import com.sirko007.cryptopulse.data.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    repository: CryptoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val coinId: String = savedStateHandle.get<String>("coinId").orEmpty()

    val coin: StateFlow<CoinEntity?> = repository.observeCoin(coinId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
}
