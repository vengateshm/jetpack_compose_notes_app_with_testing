package dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.data.FakeNoteRepository
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.model.Note
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.util.NoteSort
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.util.SortType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetNotesTest {
    private lateinit var getNotes: GetNotes
    private lateinit var fakeNoteRepository: FakeNoteRepository // Helpful in tests

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        getNotes = GetNotes(fakeNoteRepository)

        val notesToInsert = mutableListOf<Note>()
        ('a'..'z').forEachIndexed { index, c ->
            notesToInsert.add(
                Note(
                    title = c.toString(),
                    content = c.toString(),
                    timestamp = index.toLong(),
                    color = index
                )
            )
        }
        notesToInsert.shuffle()

        runBlocking {
            notesToInsert.forEach {
                fakeNoteRepository.addNote(it)
            }
        }
    }

    @Test
    fun `Sort notes by title ascending`() {
        runBlocking {
            val notes = getNotes(NoteSort.Title(SortType.Asc)).first()
            for (i in 0..notes.size - 2) {
                assertThat(notes[i].title).isLessThan(notes[i + 1].title)
            }
        }
    }

    @Test
    fun `Sort notes by title descending`() {
        runBlocking {
            val notes = getNotes(NoteSort.Title(SortType.Desc)).first()
            for (i in 0..notes.size - 2) {
                assertThat(notes[i].title).isGreaterThan(notes[i + 1].title)
            }
        }
    }

    @Test
    fun `Sort notes by date ascending`() {
        runBlocking {
            val notes = getNotes(NoteSort.Date(SortType.Asc)).first()
            for (i in 0..notes.size - 2) {
                assertThat(notes[i].timestamp).isLessThan(notes[i + 1].timestamp)
            }
        }
    }

    @Test
    fun `Sort notes by date descending`() {
        runBlocking {
            val notes = getNotes(NoteSort.Date(SortType.Desc)).first()
            for (i in 0..notes.size - 2) {
                assertThat(notes[i].timestamp).isGreaterThan(notes[i + 1].timestamp)
            }
        }
    }

    @Test
    fun `Sort notes by color ascending`() {
        runBlocking {
            val notes = getNotes(NoteSort.Color(SortType.Asc)).first()
            for (i in 0..notes.size - 2) {
                assertThat(notes[i].color).isLessThan(notes[i + 1].color)
            }
        }
    }

    @Test
    fun `Sort notes by color descending`() {
        runBlocking {
            val notes = getNotes(NoteSort.Color(SortType.Desc)).first()
            for (i in 0..notes.size - 2) {
                assertThat(notes[i].color).isGreaterThan(notes[i + 1].color)
            }
        }
    }
}