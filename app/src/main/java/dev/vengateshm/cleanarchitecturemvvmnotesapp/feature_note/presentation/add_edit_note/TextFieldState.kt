package dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.presentation.add_edit_note

data class TextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
