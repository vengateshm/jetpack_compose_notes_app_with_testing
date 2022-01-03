package dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.presentation.note_list

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.presentation.note_list.components.NoteItem
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.presentation.note_list.components.SortSection
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun NoteListScreen(
    navController: NavController,
    viewModel: NoteListViewModel = hiltViewModel()
) {
    val noteListState = viewModel.noteListState.value
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditNote.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header with sort icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "My Notes",
                    style = MaterialTheme.typography.h5
                )
                IconButton(
                    onClick = {
                        viewModel.onEvent(NotesEvent.ToggleSortSection)
                    }) {
                    Icon(imageVector = Icons.Default.Sort, contentDescription = "Sort")
                }
            }

            // Sort section
            AnimatedVisibility(
                visible = noteListState.isSortSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                SortSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    noteSort = noteListState.noteSort,
                    onSortChange = {
                        viewModel.onEvent(NotesEvent.SortNote(it))
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Note List
            LazyColumn() {
                items(noteListState.noteList) { note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Screen.AddEditNote.route + "")
                            },
                        onDeleteClicked = {
                            viewModel.onEvent(NotesEvent.DeleteNote(note))
                            coroutineScope.launch {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Note deleted",
                                    actionLabel = "Undo"
                                )
                                if (result == SnackbarResult.ActionPerformed) {
                                    viewModel.onEvent(NotesEvent.RestoreNote)
                                }
                            }
                        })
                }
            }
        }
    }
}