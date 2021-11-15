package com.berker.ultimatenoteapp.ui.note.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
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
    private var itemClickLister: ((Note) -> Unit)? = null,
) : RecyclerView.Adapter<NoteListViewHolder>() {

    private var itemList: List<Note> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListViewHolder {
        val itemBinding =
            RvItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteListViewHolder(itemBinding, itemClickLister)
    }

    override fun onBindViewHolder(holder: NoteListViewHolder, position: Int) {
        setFadeAnimation(holder.itemView)
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size
    fun setOnItemClickListener(itemClickLister: ((Note) -> Unit)?) {
        this.itemClickLister = itemClickLister
    }

    fun updateData(newItemList: List<Note>) {
        itemList = newItemList
        notifyDataSetChanged()
    }

    fun getItem(position: Int): Note {
        return itemList[position]
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 200
        view.startAnimation(anim)
    }
}