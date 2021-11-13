package com.berker.ultimatenoteapp.ui.note.list

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.berker.ultimatenoteapp.R
import com.berker.ultimatenoteapp.common.Constants
import com.berker.ultimatenoteapp.databinding.FragmentNoteListBinding
import com.berker.ultimatenoteapp.domain.model.Note
import com.berker.ultimatenoteapp.domain.model.NoteColor
import com.berker.ultimatenoteapp.domain.util.NoteOrder
import com.berker.ultimatenoteapp.domain.util.OrderType
import com.berker.ultimatenoteapp.ui.note.base.BaseFragment
import com.berker.ultimatenoteapp.ui.note.list.adapter.NoteListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NoteListFragment : BaseFragment<FragmentNoteListBinding>() {

    private val viewModel: NoteListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun initUi() {
        lifecycleScope.launchWhenCreated {
            binding.apply {
                initFilters()
                btnTestAdd.setOnClickListener {
                    viewModel.onEvent(
                        NoteListEvent.AddNote(
                            Note(
                                title = "asdad",
                                description = "adsasd",
                                createdDate = 123123213,
                                editHistory = 234213,
                                color = NoteColor.values().random().value
                            )
                        ))
                }
                viewModel.noteListState.collectLatest {
                    if (it.notes.isNotEmpty()) {
                        initRecyclerView(it.notes)
                    }
                }
            }
        }
    }

    override fun layoutId(): Int = R.layout.fragment_note_list

    private fun initRecyclerView(list: List<Note>) {
        val noteListAdapter = NoteListAdapter(list)
        noteListAdapter.setOnItemClickListener {
            findNavController().navigate(
                R.id.action_noteListFragment_to_noteDetailFragment,
                bundleOf(Pair(Constants.PARAM_NOTE_ID, it.id)))
        }
        noteListAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        val recyclerView = binding.rvNotesList
        recyclerView.adapter = noteListAdapter
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun initFilters() {
        NoteOrder::class.nestedClasses.forEach {
            createFilterView(it.simpleName)
        }
    }

    private fun createFilterView(noteOrder: String?): View {
        CheckBox(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setOnClickListener {
                //hardcoded
                when (noteOrder) {
                    "Color" -> {
                        viewModel.onEvent(NoteListEvent.Order(NoteOrder.Color(OrderType.Ascending)))
                    }
                }
            }
            text = noteOrder
            maxLines = 1
            textSize = 16f
            binding.glFiltersHolder.addView(this)
            return this
        }
    }
}