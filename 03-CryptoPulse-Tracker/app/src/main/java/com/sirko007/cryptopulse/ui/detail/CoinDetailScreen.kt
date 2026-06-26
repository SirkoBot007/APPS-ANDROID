package com.sirko007.cryptopulse.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sirko007.cryptopulse.ui.asMarketCap
import com.sirko007.cryptopulse.ui.asPercent
import com.sirko007.cryptopulse.ui.asPrice
import com.sirko007.cryptopulse.ui.components.SparklineChart
import com.sirko007.cryptopulse.ui.theme.Down
import com.sirko007.cryptopulse.ui.theme.Up

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailScreen(
    onBack: () -> Unit,
    viewModel: CoinDetailViewModel = hiltViewModel()
) {
    val coin by viewModel.coin.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(coin?.name ?: "Detalle") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        val current = coin
        if (current == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) { Text("Cargando…") }
            return@Scaffold
        }

        val trendColor = if (current.priceChange24h >= 0) Up else Down
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AsyncImage(
                    model = current.image,
                    contentDescription = current.name,
                    modifier = Modifier.size(48.dp)
                )
                Column {
                    Text(current.name, style = MaterialTheme.typography.titleLarge)
                    Text("#${current.rank} · ${current.symbol}", style = MaterialTheme.typography.labelMedium)
                }
            }

            Text(current.currentPrice.asPrice(), style = MaterialTheme.typography.headlineMedium)
            Text(
                "${current.priceChange24h.asPercent()} (24h)",
                color = trendColor,
                fontWeight = FontWeight.SemiBold
            )

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Tendencia 7 días", style = MaterialTheme.typography.titleLarge)
                    SparklineChart(
                        prices = current.sparkline,
                        color = trendColor,
                        strokeWidth = 4f,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .padding(top = 12.dp)
                    )
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    StatRow("Cap. de mercado", current.marketCap.asMarketCap())
                    StatRow("Símbolo", current.symbol)
                    StatRow("Posición", "#${current.rank}")
                }
            }

            Text(
                "Precios guardados localmente: esta pantalla funciona sin conexión tras la primera carga. " +
                    "Datos de CoinGecko.",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
private fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Text(value, fontWeight = FontWeight.SemiBold)
    }
}
