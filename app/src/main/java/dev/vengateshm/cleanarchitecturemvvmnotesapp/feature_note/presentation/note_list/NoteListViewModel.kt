package dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.presentation.note_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.model.Note
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.use_case.NotesUseCases
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.util.NoteSort
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.util.SortType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val notesUseCases: NotesUseCases
) : ViewModel() {

    private val _noteListState = mutableStateOf(NoteListState())
    val noteListState: State<NoteListState> = _noteListState

    private var noteToBeUndeleted: Note? = null
    private var getNotesJob: Job? = null

    init {
        getNotes(NoteSort.Date(SortType.Desc))
    }

    private fun getNotes(noteSort: NoteSort) {
        getNotesJob?.cancel()
        getNotesJob = notesUseCases.getNotes(noteSort)
            .onEach { notes ->
                _noteListState.value = noteListState.value
                    .copy(
                        noteList = notes,
                        noteSort = noteSort
                    )
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.SortNote -> {
                if (noteListState.value.noteSort::class == event.noteSort::class
                    && noteListState.value.noteSort.sortType == event.noteSort.sortType
                ) {
                    return
                }
                getNotes(event.noteSort)
            }
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteToBeUndeleted = event.note
                    notesUseCases.deleteNote(event.note)
                }
            }
            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    notesUseCases.addNote(noteToBeUndeleted ?: return@launch)
                    noteToBeUndeleted = null
                }
            }
            is NotesEvent.ToggleSortSection -> {
                _noteListState.value = noteListState.value
                    .copy(
                        isSortSectionVisible = noteListState.value.isSortSectionVisible.not()
                    )
            }
        }
    }
}