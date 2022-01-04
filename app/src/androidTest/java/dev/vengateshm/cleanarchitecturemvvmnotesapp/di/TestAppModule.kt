package dev.vengateshm.cleanarchitecturemvvmnotesapp.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.data.db.NoteDatabase
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.data.repository.NoteRepositoryImpl
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.repository.NoteRepository
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Provides
    @Singleton
    fun provideNotesDatabase(application: Application): NoteDatabase {
        return Room.inMemoryDatabaseBuilder(
            application,
            NoteDatabase::class.java
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDatabase: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(noteDatabase.noteDao)
    }

    @Provides
    @Singleton
    fun provideNotesUseCases(noteRepository: NoteRepository): NotesUseCases {
        return NotesUseCases(
            addNote = AddNote(noteRepository),
            deleteNote = DeleteNote(noteRepository),
            getNote = GetNote(noteRepository),
            getNotes = GetNotes(noteRepository)
        )
    }
}