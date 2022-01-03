package dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.presentation.note_list.NoteListScreen
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.presentation.util.Screen
import dev.vengateshm.cleanarchitecturemvvmnotesapp.ui.theme.CleanArchitectureMVVMNotesAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanArchitectureMVVMNotesAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NoteList.route
                    ) {
                        composable(route = Screen.NoteList.route) {
                            NoteListScreen(navController = navController)
                        }
                        composable(route = Screen.AddEditNote.route +
                                "?noteId={noteId}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(name = "noteId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(name = "noteColor") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )) {
                            val color = it.arguments?.getInt("noteColor") ?: -1
                            AddEditNoteScreen(navController = navController, noteColor = color)
                        }
                    }
                }
            }
        }
    }
}