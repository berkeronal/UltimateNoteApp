package com.berker.ultimatenoteapp.domain.repository

import com.berker.ultimatenoteapp.domain.model.Note
import kotlinx.coroutines.flow.Flow

// _________________________________
//|  _____________________________  |
//| |       ❚❚█══BERKER══█❚❚       | |
//| |    Created by Berker ÖNAL   | |
//| |     berkeronal@gmail.com    | |
//| |         12.11.2021          | |
//| |_____________________________| |
//|_________________________________|
interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)
}