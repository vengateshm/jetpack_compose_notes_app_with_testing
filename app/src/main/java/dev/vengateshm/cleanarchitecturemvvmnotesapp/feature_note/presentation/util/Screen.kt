package dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.presentation.util

sealed class Screen(val route: String) {
    object NoteList : Screen("note_list")
    object AddEditNote : Screen("add_edit_note")
}
