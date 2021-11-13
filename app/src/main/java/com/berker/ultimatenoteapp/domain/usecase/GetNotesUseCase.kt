package com.berker.ultimatenoteapp.domain.usecase

import com.berker.ultimatenoteapp.domain.model.Note
import com.berker.ultimatenoteapp.domain.repository.NoteRepository
import com.berker.ultimatenoteapp.domain.util.NoteOrder
import com.berker.ultimatenoteapp.domain.util.NoteOrder.*
import com.berker.ultimatenoteapp.domain.util.OrderType.Ascending
import com.berker.ultimatenoteapp.domain.util.OrderType.Descending
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// _________________________________
//|  _____________________________  |
//| |       ❚❚█══BERKER══█❚❚       | |
//| |    Created by Berker ÖNAL   | |
//| |     berkeronal@gmail.com    | |
//| |         13.11.2021          | |
//| |_____________________________| |
//|_________________________________|
class GetNotesUseCase(
    private val repository: NoteRepository,
) {

    operator fun invoke(
        noteOrder: NoteOrder = CreatedDate(Descending),
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (noteOrder.orderType) {
                is Ascending -> {
                    when (noteOrder) {
                        is Title -> notes.sortedBy { it.title.lowercase() }
                        is CreatedDate -> notes.sortedBy { it.createdDate }
                        is Color -> notes.sortedBy { it.color }
                        is EditDate -> notes.sortedBy { it.editHistory }
                    }
                }
                is Descending -> {
                    when (noteOrder) {
                        is Title -> notes.sortedByDescending { it.title.lowercase() }
                        is CreatedDate -> notes.sortedByDescending { it.createdDate }
                        is Color -> notes.sortedByDescending { it.color }
                        is EditDate -> notes.sortedByDescending { it.editHistory }
                    }
                }
            }
        }
    }
}