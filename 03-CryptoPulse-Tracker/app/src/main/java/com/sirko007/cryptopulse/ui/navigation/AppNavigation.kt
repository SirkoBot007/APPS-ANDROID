package com.sirko007.cryptopulse.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sirko007.cryptopulse.ui.detail.CoinDetailScreen
import com.sirko007.cryptopulse.ui.list.CoinListScreen

object Routes {
    const val LIST = "coins"
    const val DETAIL = "detail"
    const val ARG_COIN_ID = "coinId"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LIST) {
        composable(Routes.LIST) {
            CoinListScreen(
                onOpenCoin = { id -> navController.navigate("${Routes.DETAIL}/$id") }
            )
        }
        composable(
            route = "${Routes.DETAIL}/{${Routes.ARG_COIN_ID}}",
            arguments = listOf(navArgument(Routes.ARG_COIN_ID) { type = NavType.StringType })
        ) {
            CoinDetailScreen(onBack = { navController.popBackStack() })
        }
    }
}
