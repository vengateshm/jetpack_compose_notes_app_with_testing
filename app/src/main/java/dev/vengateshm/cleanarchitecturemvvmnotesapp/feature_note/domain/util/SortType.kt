package dev.vengateshm.cleanarchitecturemvvmnotesapp.feature_note.domain.util

// General sort types
sealed class SortType {
    object Asc : SortType()
    object Desc : SortType()
}
