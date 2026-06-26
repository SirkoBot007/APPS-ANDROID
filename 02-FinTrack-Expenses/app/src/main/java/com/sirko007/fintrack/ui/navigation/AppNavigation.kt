package com.sirko007.fintrack.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sirko007.fintrack.ui.add.AddTransactionScreen
import com.sirko007.fintrack.ui.home.HomeScreen

object Routes {
    const val HOME = "home"
    const val ADD = "add"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(onAddTransaction = { navController.navigate(Routes.ADD) })
        }
        composable(Routes.ADD) {
            AddTransactionScreen(onBack = { navController.popBackStack() })
        }
    }
}
