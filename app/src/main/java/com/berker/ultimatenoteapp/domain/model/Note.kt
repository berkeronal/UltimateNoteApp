package com.berker.ultimatenoteapp.domain.model

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
    ORANGE(R.color.note_orange),
    RED(R.color.note_red),
}

enum class NoteColorDark(val value: Int) {
    BLUE_DARK(R.color.note_blue_dark),
    AQUA_DARK(R.color.note_aqua_dark),
    MINT_DARK(R.color.note_mint_dark),
    GREEN_DARK(R.color.note_green_dark),
    ORANGE_DARK(R.color.note_orange_dark),
    RED_DARK(R.color.note_red_dark),
}

class InvalidNoteException(message: String) : Exception(message)