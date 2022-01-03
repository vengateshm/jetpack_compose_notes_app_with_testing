package dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.data.repository

import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.data.db.NoteDao
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.model.Note
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val dao: NoteDao) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun addNote(note: Note) {
        return dao.insert(note)
    }

    override suspend fun deleteNote(note: Note) {
        return dao.delete(note)
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }
}