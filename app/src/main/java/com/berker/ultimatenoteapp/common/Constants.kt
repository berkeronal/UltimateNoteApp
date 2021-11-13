package com.berker.ultimatenoteapp.common

import com.berker.ultimatenoteapp.domain.util.NoteOrder
import com.berker.ultimatenoteapp.domain.util.OrderType

object Constants {

    val DEFAULT_MAIN_PAGE = NoteOrder.CreatedDate(OrderType.Descending)
}