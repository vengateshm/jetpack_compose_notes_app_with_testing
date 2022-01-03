package dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.presentation.note_list

import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.model.Note
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.util.NoteSort

sealed class NotesEvent {
    data class SortNote(val noteSort: NoteSort) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent()
    object RestoreNote : NotesEvent()
    object ToggleSortSection : NotesEvent()
}
