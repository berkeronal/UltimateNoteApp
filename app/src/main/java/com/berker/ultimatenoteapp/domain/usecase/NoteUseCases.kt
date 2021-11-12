package com.berker.ultimatenoteapp.domain.usecase

data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val getNoteUseCase: GetNoteUseCase,
    val addNoteUseCase: AddNoteUseCase,
)
