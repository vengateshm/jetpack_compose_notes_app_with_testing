package dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.util

sealed class NoteSort(val sortType: SortType) {
    class Title(sortType: SortType) : NoteSort(sortType)
    class Date(sortType: SortType) : NoteSort(sortType)
    class Color(sortType: SortType) : NoteSort(sortType)

    fun copy(sortType: SortType): NoteSort {
        return when (this) {
            is Title -> Title(sortType)
            is Date -> Date(sortType)
            is Color -> Color(sortType)
        }
    }
}
