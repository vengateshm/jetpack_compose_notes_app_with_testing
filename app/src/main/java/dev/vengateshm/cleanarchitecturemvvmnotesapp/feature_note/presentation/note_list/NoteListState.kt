package dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.presentation.note_list

import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.model.Note
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.util.NoteSort
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.util.SortType

data class NoteListState(
    val noteList: List<Note> = emptyList(),
    val noteSort: NoteSort = NoteSort.Date(SortType.Asc),
    val isSortSectionVisible: Boolean = false
)
