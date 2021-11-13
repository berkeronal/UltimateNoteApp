package com.berker.ultimatenoteapp.ui.note.list

import com.berker.ultimatenoteapp.domain.model.Note
import com.berker.ultimatenoteapp.domain.util.NoteOrder

sealed class NoteListEvent {
    data class Order(val noteOrder: NoteOrder) : NoteListEvent()
    data class DeleteNote(val note: Note) : NoteListEvent()
    data class AddNote(val note: Note) : NoteListEvent()
    object ToggleFilterTab : NoteListEvent()
}
