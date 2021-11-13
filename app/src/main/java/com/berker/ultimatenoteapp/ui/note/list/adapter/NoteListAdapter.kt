package com.berker.ultimatenoteapp.ui.note.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
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
class NoteListAdapter(
    private val itemList: List<Note>,
    private var itemClickLister: ((Note) -> Unit)? = null
) : RecyclerView.Adapter<NoteListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListViewHolder {
        val itemBinding =
            RvItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteListViewHolder(itemBinding,itemClickLister)
    }

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
    fun setOnItemClickListener(itemClickLister: ((Note) -> Unit)?) {
        this.itemClickLister = itemClickLister
    }
}