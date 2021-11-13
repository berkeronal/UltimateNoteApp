package com.berker.ultimatenoteapp.ui.note.list.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.berker.ultimatenoteapp.databinding.RvItemNoteBinding
import com.berker.ultimatenoteapp.domain.model.Note

// _________________________________
//|  _____________________________  |
//| |       ❚❚█══BERKER══█❚❚       | |
//| |    Created by Berker ÖNAL   | |
//| |     berkeronal@gmail.com    | |
//| |         13.11.2021          | |
//| |_____________________________| |
//|_________________________________|
class NoteListViewHolder(
    private val itemBinding: RvItemNoteBinding,
    private val clickedItem: ((Note) -> Unit)?,
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(note: Note) {
        itemView.setOnClickListener {
            clickedItem?.invoke(note)
        }
        itemBinding.apply {
            ivNote.visibility = if (note.imageUrl == "") {
                View.VISIBLE
            } else {
                View.VISIBLE
            }
            cvRootCard.setCardBackgroundColor(itemBinding.root.resources.getColor(note.color, null))
            tvTitle.text = note.title
            tvDescription.text = note.description
            tvDate.text = note.createdDate.toString()
        }
    }
}