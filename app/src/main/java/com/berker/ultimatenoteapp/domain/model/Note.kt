package com.berker.ultimatenoteapp.domain.model

import androidx.annotation.IdRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.berker.ultimatenoteapp.R

// _________________________________
//|  _____________________________  |
//| |       ❚❚█══BERKER══█❚❚       | |
//| |    Created by Berker ÖNAL   | |
//| |     berkeronal@gmail.com    | |
//| |         12.11.2021          | |
//| |_____________________________| |
//|_________________________________|
@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val description: String,
    val imageUrl: String = "",
    val createdDate: Long,
    val editHistory: Long,
    val color: Int,
)

enum class NoteColor(val value: Int) {
    BLUE(R.color.note_blue),
    AQUA(R.color.note_aqua),
    MINT(R.color.note_mint),
    GREEN(R.color.note_green),
    YELLOW(R.color.note_yellow),
    RED(R.color.note_red),

}

class InvalidNoteException(message: String) : Exception(message)