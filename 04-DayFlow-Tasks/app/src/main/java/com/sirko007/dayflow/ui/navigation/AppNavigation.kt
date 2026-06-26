package com.sirko007.dayflow.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sirko007.dayflow.ui.edit.EditTaskScreen
import com.sirko007.dayflow.ui.tasks.TasksScreen

object Routes {
    const val LIST = "tasks"
    const val EDIT = "edit"
    const val ARG_TASK_ID = "taskId"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LIST) {
        composable(Routes.LIST) {
            TasksScreen(
                onAddTask = { navController.navigate("${Routes.EDIT}/0") },
                onOpenTask = { id -> navController.navigate("${Routes.EDIT}/$id") }
            )
        }
        composable(
            route = "${Routes.EDIT}/{${Routes.ARG_TASK_ID}}",
            arguments = listOf(navArgument(Routes.ARG_TASK_ID) { type = NavType.LongType })
        ) {
            EditTaskScreen(onBack = { navController.popBackStack() })
        }
    }
}
