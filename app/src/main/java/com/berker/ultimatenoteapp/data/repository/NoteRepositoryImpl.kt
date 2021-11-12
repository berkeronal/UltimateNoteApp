package com.berker.ultimatenoteapp.data.repository

import com.berker.ultimatenoteapp.data.datasource.NoteDao
import com.berker.ultimatenoteapp.domain.model.Note
import com.berker.ultimatenoteapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

// _________________________________
//|  _____________________________  |
//| |       ❚❚█══BERKER══█❚❚       | |
//| |    Created by Berker ÖNAL   | |
//| |     berkeronal@gmail.com    | |
//| |         12.11.2021          | |
//| |_____________________________| |
//|_________________________________|
class NoteRepositoryImpl(
    private val dao: NoteDao,
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }
}