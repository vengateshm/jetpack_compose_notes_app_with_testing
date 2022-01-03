package dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.repository

import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<List<Note>>
    suspend fun addNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun getNoteById(id: Int): Note?
}