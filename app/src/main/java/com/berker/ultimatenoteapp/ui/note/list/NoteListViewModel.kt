package com.berker.ultimatenoteapp.ui.note.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berker.ultimatenoteapp.common.Constants.DEFAULT_MAIN_PAGE
import com.berker.ultimatenoteapp.domain.usecase.NoteUseCases
import com.berker.ultimatenoteapp.domain.util.NoteOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
class NoteListViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
) : ViewModel() {

    private val _noteListState = MutableStateFlow(NoteListState())
    val noteListState: StateFlow<NoteListState> = _noteListState
    private var getNotesJob: Job? = null

    init {
        getNotesWithNoteOrder(DEFAULT_MAIN_PAGE)
    }

    fun onEvent(event: NoteListEvent) {
        when (event) {
            is NoteListEvent.AddNote -> {
                viewModelScope.launch {
                    noteUseCases.addNoteUseCase(event.note)
                }
            }
            is NoteListEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNoteUseCase(event.note)
                }
            }
            is NoteListEvent.Order -> {
                if (!isOrderDifferent(event.noteOrder)) {
                    return
                }
                getNotesWithNoteOrder(event.noteOrder)
            }
            is NoteListEvent.ToggleFilterTab -> {
                _noteListState.value = noteListState.value.copy(
                    isFilterTabVisible = !noteListState.value.isFilterTabVisible
                )
            }
        }
    }

    private fun isOrderDifferent(noteOrder: NoteOrder): Boolean {
        return noteListState.value.noteOrder::class != noteOrder::class &&
                noteListState.value.noteOrder.orderType != noteOrder.orderType
    }

    private fun getNotesWithNoteOrder(noteOrder: NoteOrder) {
        getNotesJob?.cancel()
        getNotesJob = noteUseCases.getNotesUseCase(noteOrder)
            .onEach { notes ->
                _noteListState.value = noteListState.value.copy(
                    notes = notes,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }
}