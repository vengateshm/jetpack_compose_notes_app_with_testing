package dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.presentation.note_list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dev.vengateshm.cleanarchitecturemvvmnotesapp.core.util.TestTags
import dev.vengateshm.cleanarchitecturemvvmnotesapp.di.AppModule
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.presentation.MainActivity
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.presentation.util.Screen
import dev.vengateshm.cleanarchitecturemvvmnotesapp.ui.theme.CleanArchitectureMVVMNotesAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class NoteListScreenTest {
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
                }
            }
        }
    }

    @Test
    fun Verify_sort_section_visible_on_click_sort_button() {
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertDoesNotExist()
        composeRule.onNodeWithContentDescription("Sort").performClick() // Use string resources for content description
        composeRule.onNodeWithTag(TestTags.ORDER_SECTION).assertIsDisplayed()
    }
}