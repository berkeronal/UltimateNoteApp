package com.berker.ultimatenoteapp.ui.note.list

import com.berker.ultimatenoteapp.domain.model.Note
import com.berker.ultimatenoteapp.domain.util.NoteOrder
import com.berker.ultimatenoteapp.domain.util.OrderType

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.CreatedDate(OrderType.Descending),
    val isFilterTabVisible: Boolean = false,
)
