package dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.presentation.note_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.util.NoteSort
import dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.util.SortType

@Composable
fun SortSection(
    modifier: Modifier = Modifier,
    noteSort: NoteSort = NoteSort.Date(SortType.Desc),
    onSortChange: (NoteSort) -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Title",
                isSelected = noteSort is NoteSort.Title,
                onSelect = { onSortChange(NoteSort.Title(noteSort.sortType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                isSelected = noteSort is NoteSort.Date,
                onSelect = { onSortChange(NoteSort.Date(noteSort.sortType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Color",
                isSelected = noteSort is NoteSort.Color,
                onSelect = { onSortChange(NoteSort.Color(noteSort.sortType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                isSelected = noteSort.sortType is SortType.Asc,
                onSelect = {
                    onSortChange(noteSort.copy(SortType.Asc))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                isSelected = noteSort.sortType is SortType.Desc,
                onSelect = {
                    onSortChange(noteSort.copy(SortType.Desc))
                }
            )
        }
    }
}