package dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.use_case

data class NotesUseCases constructor(
    val addNote: AddNote,
    val deleteNote: DeleteNote,
    val getNote: GetNote,
    val getNotes: GetNotes
)