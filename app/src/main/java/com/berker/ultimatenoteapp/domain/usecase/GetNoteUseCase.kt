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
class GetNoteUseCase(
    private val repository: NoteRepository,
) {

    suspend operator fun invoke(coinId: Int): Note? {
        return repository.getNoteById(coinId)
    }
}