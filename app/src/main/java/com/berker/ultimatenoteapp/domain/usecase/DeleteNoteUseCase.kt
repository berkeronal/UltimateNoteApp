package com.berker.ultimatenoteapp.domain.usecase

import com.berker.ultimatenoteapp.domain.model.Note
import com.berker.ultimatenoteapp.domain.repository.NoteRepository

// _________________________________
//|  _____________________________  |
//| |       ❚❚█══BERKER══█❚❚       | |
//| |    Created by Berker ÖNAL   | |
//| |     berkeronal@gmail.com    | |
//| |         13.11.2021          | |
//| |_____________________________| |
//|_________________________________|
class DeleteNoteUseCase(
    private val repository: NoteRepository,
) {

    suspend operator fun invoke(note: Note) {
        repository.deleteNote(note)
    }
}