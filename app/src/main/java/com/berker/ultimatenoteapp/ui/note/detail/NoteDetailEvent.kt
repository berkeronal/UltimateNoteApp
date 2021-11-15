package com.berker.ultimatenoteapp.ui.note.detail

// _________________________________
//|  _____________________________  |
//| |       ❚❚█══BERKER══█❚❚       | |
//| |    Created by Berker ÖNAL   | |
//| |     berkeronal@gmail.com    | |
//| |         14.11.2021          | |
//| |_____________________________| |
//|_________________________________|
sealed class NoteDetailEvent {

    object SaveNote : NoteDetailEvent()
    data class WroteTitle(val title: String) : NoteDetailEvent()
    data class WroteImageUrl(val imageUrl: String) : NoteDetailEvent()
    data class WroteDescription(val description: String) : NoteDetailEvent()
    data class ChangeColor(val color: Int) : NoteDetailEvent()
}
