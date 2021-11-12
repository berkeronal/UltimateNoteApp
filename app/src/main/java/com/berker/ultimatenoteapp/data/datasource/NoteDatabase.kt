package com.berker.ultimatenoteapp.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.berker.ultimatenoteapp.domain.model.Note

// _________________________________
//|  _____________________________  |
//| |       ❚❚█══BERKER══█❚❚       | |
//| |    Created by Berker ÖNAL   | |
//| |     berkeronal@gmail.com    | |
//| |         12.11.2021          | |
//| |_____________________________| |
//|_________________________________|
@Database(
    entities = [Note::class],
    version = 1,
)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDao: NoteDao
}