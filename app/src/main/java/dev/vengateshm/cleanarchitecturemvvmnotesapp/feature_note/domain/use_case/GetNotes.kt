package dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.use_case

import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.model.Note
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.repository.NoteRepository
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.util.NoteSort
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.util.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(private val repository: NoteRepository) {
    operator fun invoke(noteSort: NoteSort = NoteSort.Date(SortType.Desc)): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (noteSort.sortType) {
                is SortType.Asc -> {
                    when (noteSort) {
                        is NoteSort.Title -> notes.sortedBy { it.title.lowercase() }
                        is NoteSort.Date -> notes.sortedBy { it.timestamp }
                        is NoteSort.Color -> notes.sortedBy { it.color }
                    }
                }
                is SortType.Desc -> {
                    when (noteSort) {
                        is NoteSort.Title -> notes.sortedByDescending { it.title.lowercase() }
                        is NoteSort.Date -> notes.sortedByDescending { it.timestamp }
                        is NoteSort.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}