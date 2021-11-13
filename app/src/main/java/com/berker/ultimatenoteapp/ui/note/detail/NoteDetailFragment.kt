package com.berker.ultimatenoteapp.ui.note.detail

import android.os.Bundle
import com.berker.ultimatenoteapp.R
import com.berker.ultimatenoteapp.databinding.FragmentNoteDetailBinding
import com.berker.ultimatenoteapp.ui.note.base.BaseFragment

class NoteDetailFragment : BaseFragment<FragmentNoteDetailBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun layoutId(): Int = R.layout.fragment_note_detail
}