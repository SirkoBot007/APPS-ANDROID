package com.sirko007.smartnotes.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sirko007.smartnotes.ui.editor.NoteEditorScreen
import com.sirko007.smartnotes.ui.notes.NotesListScreen

object Routes {
    const val LIST = "notes"
    const val EDITOR = "editor"
    const val ARG_NOTE_ID = "noteId"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LIST) {
        composable(Routes.LIST) {
            NotesListScreen(
                onAddNote = { navController.navigate("${Routes.EDITOR}/0") },
                onOpenNote = { id -> navController.navigate("${Routes.EDITOR}/$id") }
            )
        }
        composable(
            route = "${Routes.EDITOR}/{${Routes.ARG_NOTE_ID}}",
            arguments = listOf(navArgument(Routes.ARG_NOTE_ID) { type = NavType.LongType })
        ) {
            NoteEditorScreen(onBack = { navController.popBackStack() })
        }
    }
}
