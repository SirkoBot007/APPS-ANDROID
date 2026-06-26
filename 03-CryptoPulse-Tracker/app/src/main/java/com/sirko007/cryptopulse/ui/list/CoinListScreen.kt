package com.sirko007.cryptopulse.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sirko007.cryptopulse.data.local.CoinEntity
import com.sirko007.cryptopulse.ui.asPercent
import com.sirko007.cryptopulse.ui.asPrice
import com.sirko007.cryptopulse.ui.components.SparklineChart
import com.sirko007.cryptopulse.ui.theme.Down
import com.sirko007.cryptopulse.ui.theme.Up

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinListScreen(
    onOpenCoin: (String) -> Unit,
    viewModel: CoinListViewModel = hiltViewModel()
) {
    val coins by viewModel.coins.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    androidx.compose.runtime.LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.dismissError()
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("CryptoPulse") }) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = viewModel::refresh,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (coins.isEmpty() && !uiState.isRefreshing) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Aún no hay datos. Desliza hacia abajo para actualizar.")
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(coins, key = { it.id }) { coin ->
                        CoinRow(coin = coin, onClick = { onOpenCoin(coin.id) })
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}

@Composable
private fun CoinRow(coin: CoinEntity, onClick: () -> Unit) {
    val trendColor = if (coin.priceChange24h >= 0) Up else Down
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = coin.image,
            contentDescription = coin.name,
            modifier = Modifier.size(36.dp)
        )
        Column(modifier = Modifier.width(96.dp)) {
            Text(coin.symbol, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge)
            Text(coin.name, style = MaterialTheme.typography.labelMedium, maxLines = 1)
        }
        SparklineChart(
            prices = coin.sparkline,
            color = trendColor,
            modifier = Modifier
                .weight(1f)
                .height(36.dp)
        )
        Column(horizontalAlignment = Alignment.End, modifier = Modifier.width(96.dp)) {
            Text(coin.currentPrice.asPrice(), fontWeight = FontWeight.SemiBold)
            Text(coin.priceChange24h.asPercent(), color = trendColor, style = MaterialTheme.typography.labelMedium)
        }
    }
}
