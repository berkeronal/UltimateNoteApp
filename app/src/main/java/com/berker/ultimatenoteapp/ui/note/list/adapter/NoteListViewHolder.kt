package com.berker.ultimatenoteapp.ui.note.list.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.berker.ultimatenoteapp.R
import com.berker.ultimatenoteapp.databinding.RvItemNoteBinding
import com.berker.ultimatenoteapp.domain.model.Note
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

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
            if (note.imageUrl != "") {
                Glide.with(root.context)
                    .load(note.imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(ivNote)
            } else {
                Glide.with(root.context)
                    .load(R.drawable.ic_launcher_foreground)
                    .centerCrop()
                    .into(ivNote)
            }
            cvRootCard.setCardBackgroundColor(itemBinding.root.resources.getColor(note.color, null))
            tvTitle.text = note.title
            tvDescription.text = note.description
            tvDate.text = if (note.createdDate != note.editHistory) {
                "Edited at : " + convertLongToTime(note.editHistory)
            } else {
                convertLongToTime(note.createdDate)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd.MM.yyy")
        return format.format(date)
    }
}