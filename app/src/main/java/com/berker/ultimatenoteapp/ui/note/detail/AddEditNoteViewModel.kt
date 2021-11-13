package com.berker.ultimatenoteapp.ui.note.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.berker.ultimatenoteapp.common.Constants
import com.berker.ultimatenoteapp.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

// _________________________________
//|  _____________________________  |
//| |       ❚❚█══BERKER══█❚❚       | |
//| |    Created by Berker ÖNAL   | |
//| |     berkeronal@gmail.com    | |
//| |         13.11.2021          | |
//| |_____________________________| |
//|_________________________________|
@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    init {
        savedStateHandle.get<Int>(Constants.PARAM_NOTE_ID)?.let { noteId ->
            restoreNote(noteId)
        }
    }
    private fun restoreNote(id:Int){

    }
}