package com.berker.ultimatenoteapp.ui.note.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

// _________________________________
//|  _____________________________  |
//| |       ❚❚█══BERKER══█❚❚       | |
//| |    Created by Berker ÖNAL   | |
//| |     berkeronal@gmail.com    | |
//| |         14.11.2021          | |
//| |_____________________________| |
//|_________________________________|
class NotesItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State,
    ) {
        with(outRect) {
            top =
                if (parent.getChildAdapterPosition(view) == 0) {
                    spaceHeight
                } else 0
            left = spaceHeight
            right = spaceHeight
            bottom = spaceHeight
        }
    }
}