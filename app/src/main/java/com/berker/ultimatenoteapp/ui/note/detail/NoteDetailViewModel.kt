package com.berker.ultimatenoteapp.ui.note.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berker.ultimatenoteapp.common.Constants
import com.berker.ultimatenoteapp.domain.model.Note
import com.berker.ultimatenoteapp.domain.usecase.NoteUseCases
import com.berker.ultimatenoteapp.ui.note.detail.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
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
class NoteDetailViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle1: SavedStateHandle,
) : ViewModel() {

    private var currentNoteId: Int? = null
    private val _noteDetailState = MutableStateFlow(NoteDetailState())
    val noteDetailState: StateFlow<NoteDetailState> = _noteDetailState
    private val _uiEventFlow = MutableSharedFlow<UiEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    init {
        savedStateHandle1.get<Int>(Constants.PARAM_NOTE_ID)?.let { noteId ->
            if (noteId != -1) {
                restoreNote(noteId)
            }
        }
    }

    fun onEvent(event: NoteDetailEvent) {
        when (event) {
            is NoteDetailEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        _noteDetailState.value.apply {
                            noteUseCases.addNoteUseCase(
                                Note(
                                    title = title,
                                    description = description,
                                    imageUrl = imageUrl,
                                    color = color,
                                    createdDate = if (createdDate != 0L) createdDate else System.currentTimeMillis(),
                                    editHistory = System.currentTimeMillis(),
                                    id = currentNoteId
                                )
                            )
                            _uiEventFlow.emit(UiEvent.SaveNote)
                        }
                    } catch (e: Exception) {
                        _uiEventFlow.emit(UiEvent.ShowErrorDialog(e.message
                            ?: "Error while saving note"))
                    }
                }

            }
            is NoteDetailEvent.WroteImageUrl -> {
                _noteDetailState.value = noteDetailState.value.copy(
                    imageUrl = event.imageUrl
                )
            }
            is NoteDetailEvent.ChangeColor -> {
                _noteDetailState.value = noteDetailState.value.copy(
                    color = event.color
                )
            }
            is NoteDetailEvent.WroteDescription -> {
                _noteDetailState.value = noteDetailState.value.copy(
                    description = event.description
                )
            }
            is NoteDetailEvent.WroteTitle -> {
                _noteDetailState.value = noteDetailState.value.copy(
                    title = event.title
                )
            }
        }
    }

    private fun restoreNote(id: Int) {
        currentNoteId = id
        try {
            viewModelScope.launch {
                noteUseCases.getNoteUseCase(id)?.also { note ->
                    _noteDetailState.value = noteDetailState.value.copy(
                        title = note.title,
                        imageUrl = note.imageUrl,
                        createdDate = note.createdDate,
                        description = note.description,
                        color = note.color
                    )
                }
            }
        } catch (e: Exception) {
        }
    }
}