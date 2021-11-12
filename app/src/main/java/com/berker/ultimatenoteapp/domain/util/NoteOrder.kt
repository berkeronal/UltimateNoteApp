package com.berker.ultimatenoteapp.domain.util

import com.berker.ultimatenoteapp.domain.model.Note

sealed class NoteOrder(val orderType: OrderType) {

    class Title(orderType: OrderType) : NoteOrder(orderType)
    class CreatedDate(orderType: OrderType) : NoteOrder(orderType)
    class Color(orderType: OrderType) : NoteOrder(orderType)
    class EditDate(orderType: OrderType) : NoteOrder(orderType)
}
