package com.berker.ultimatenoteapp.ui.note.list

import android.os.Bundle
import com.berker.ultimatenoteapp.R
import com.berker.ultimatenoteapp.databinding.FragmentNoteListBinding
import com.berker.ultimatenoteapp.ui.note.base.BaseFragment

class NoteListFragment : BaseFragment<FragmentNoteListBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun layoutId(): Int = R.layout.fragment_note_list
}