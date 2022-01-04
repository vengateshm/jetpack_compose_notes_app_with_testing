package dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.data.FakeNoteRepository
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.model.InvalidNoteException
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.model.Note
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class AddNoteTest {

    private lateinit var addNote: AddNote
    private lateinit var getNotes: GetNotes
    private lateinit var fakeNoteRepository: FakeNoteRepository

    @Before
    fun setUp() {
        fakeNoteRepository = FakeNoteRepository()
        addNote = AddNote(fakeNoteRepository)
        getNotes = GetNotes(fakeNoteRepository)
    }

    @Test
    fun `Throws exception when title is blank`() {
        val exception = assertThrows(InvalidNoteException::class.java) {
            runBlocking {
                addNote(
                    Note(
                        title = "",
                        content = "",
                        timestamp = 1000L,
                        color = 1
                    )
                )
            }
        }
        assertThat(exception).hasMessageThat().contains("Note title cannot be empty!")
        assertThat(exception).isInstanceOf(InvalidNoteException::class.java)
    }

    @Test
    fun `Throws exception when content is blank`() {
        val exception = assertThrows(InvalidNoteException::class.java) {
            runBlocking {
                addNote(
                    Note(
                        title = "title",
                        content = "",
                        timestamp = 1000L,
                        color = 1
                    )
                )
            }
        }
        assertThat(exception).hasMessageThat().contains("Note content cannot be empty!")
        assertThat(exception).isInstanceOf(InvalidNoteException::class.java)
    }

    @Test
    fun `Verify note added`() {
        val newNote = Note(
            title = "title",
            content = "content",
            timestamp = 1000L,
            color = 1
        )
        runBlocking {
            addNote(newNote)
            assertThat(getNotes().first().contains(newNote))
        }
    }
}