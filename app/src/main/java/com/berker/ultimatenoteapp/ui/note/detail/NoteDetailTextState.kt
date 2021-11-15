package com.berker.ultimatenoteapp.ui.note.detail

import com.berker.ultimatenoteapp.domain.model.NoteColor

// _________________________________
//|  _____________________________  |
//| |       ❚❚█══BERKER══█❚❚       | |
//| |    Created by Berker ÖNAL   | |
//| |     berkeronal@gmail.com    | |
//| |         14.11.2021          | |
//| |_____________________________| |
//|_________________________________|
data class NoteDetailState(
    val title: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val createdDate: Long = 0,
    val color: Int = NoteColor.values().random().value,
)