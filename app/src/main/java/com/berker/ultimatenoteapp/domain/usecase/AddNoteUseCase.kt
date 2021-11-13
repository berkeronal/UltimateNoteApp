package com.berker.ultimatenoteapp.domain.usecase

import com.berker.ultimatenoteapp.domain.model.InvalidNoteException
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
class AddNoteUseCase(
    private val repository: NoteRepository,
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(newNote: Note) {
        if (!isNoteValid(newNote)) {
            throw InvalidNoteException("Please enter a valid note")
        }
        repository.insertNote(newNote)
    }

    private fun isNoteValid(note: Note): Boolean {
        note.apply {
            return if (title.isBlank()) {
                false
            } else description != ""
        }
    }
}