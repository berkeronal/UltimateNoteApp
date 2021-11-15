package com.berker.ultimatenoteapp.ui.note.detail.util

sealed class UiEvent {
    data class ShowErrorDialog(val message: String) : UiEvent()
    object SaveNote : UiEvent()
}
