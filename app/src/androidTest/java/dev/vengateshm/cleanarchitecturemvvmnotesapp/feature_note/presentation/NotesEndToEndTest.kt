package dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dev.vengateshm.cleanarchitecturemvvmnotesapp.core.util.TestTags
import dev.vengateshm.cleanarchitecturemvvmnotesapp.di.AppModule
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.presentation.add_edit_note.AddEditNoteScreen
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.presentation.note_list.NoteListScreen
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.presentation.util.Screen
import dev.vengateshm.cleanarchitecturemvvmnotesapp.ui.theme.CleanArchitectureMVVMNotesAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NotesEndToEndTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject() // Hilt rule to inject dependencies in test environment

        // Setup composable with nav host
        composeRule.setContent {
            val navController = rememberNavController()

            CleanArchitectureMVVMNotesAppTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.NoteList.route
                ) {
                    composable(Screen.NoteList.route) {
                        NoteListScreen(navController = navController)
                    }
                    composable(route = Screen.AddEditNote.route +
                            "?noteId={noteId}&noteColor={noteColor}",
                        arguments = listOf(
                            navArgument(name = "noteId") {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(name = "noteColor") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )) {
                        val color = it.arguments?.getInt("noteColor") ?: -1
                        AddEditNoteScreen(navController = navController, noteColor = color)
                    }
                }
            }
        }
    }

    @Test
    fun saveNewNote_editAfterwards() {
        // Click on fab to save note
        composeRule
            .onNodeWithContentDescription("Add note")
            .performClick()

        // Enter texts in title and content text fields
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("test-title")
        composeRule
            .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
            .performTextInput("test-content")

        // Save the note
        composeRule
            .onNodeWithContentDescription("Save note")
            .performClick()

        // Verify the saved note title and content
        composeRule.onNodeWithText("test-title").assertIsDisplayed()

        // Click on title to edit
        composeRule.onNodeWithText("test-title").performClick()

        // Verify the title and content field has proper texts
        composeRule.onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .assertTextEquals("test-title")
        composeRule.onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
            .assertTextEquals("test-content")

        // Update the tile
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("2")

        // Save the updated note
        composeRule.onNodeWithContentDescription("Save note").performClick()

        // Verify the updated title of the note
        composeRule.onNodeWithText("test-title2").assertIsDisplayed()
    }

    @Test
    fun saveNewNotes_orderByTitleDescending() {
        for (i in 1..3) {
            // Click on FAB to get to add note screen
            composeRule.onNodeWithContentDescription("Add note").performClick()

            // Enter texts in title and content text fields
            composeRule
                .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
                .performTextInput(i.toString())
            composeRule
                .onNodeWithTag(TestTags.CONTENT_TEXT_FIELD)
                .performTextInput(i.toString())
            // Save the new
            composeRule.onNodeWithContentDescription("Save note").performClick()
        }

        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()

        // Click sort section
        composeRule
            .onNodeWithContentDescription("Sort")
            .performClick()

        composeRule
            .onNodeWithContentDescription("Title")
            .performClick()

        composeRule
            .onNodeWithContentDescription("Descending")
            .performClick()

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0]
            .assertTextContains("3")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1]
            .assertTextContains("2")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2]
            .assertTextContains("1")
    }
}