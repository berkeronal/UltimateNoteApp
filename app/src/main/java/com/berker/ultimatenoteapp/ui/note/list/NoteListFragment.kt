package com.berker.ultimatenoteapp.ui.note.list

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Transition
import android.transition.TransitionManager
import android.view.Gravity
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.berker.ultimatenoteapp.R
import com.berker.ultimatenoteapp.common.Constants
import com.berker.ultimatenoteapp.databinding.FragmentNoteListBinding
import com.berker.ultimatenoteapp.domain.model.Note
import com.berker.ultimatenoteapp.domain.util.NoteOrder
import com.berker.ultimatenoteapp.domain.util.OrderType
import com.berker.ultimatenoteapp.ui.note.base.BaseFragment
import com.berker.ultimatenoteapp.ui.note.list.adapter.NoteListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteListFragment : BaseFragment<FragmentNoteListBinding>() {

    private val viewModel: NoteListViewModel by viewModels()
    private val noteListAdapter: NoteListAdapter by lazy {
        NoteListAdapter().apply {
            stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            setOnItemClickListener {
                findNavController().navigate(
                    R.id.action_noteListFragment_to_noteDetailFragment,
                    bundleOf(Pair(Constants.PARAM_NOTE_ID, it.id)))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun initUi() {
        initFilters()
        initRecyclerView()
        initSwipe()
        binding.fabAddNewNote.setOnClickListener {
            findNavController().navigate(
                R.id.action_noteListFragment_to_noteDetailFragment,
                bundleOf(Pair(Constants.PARAM_NOTE_ID, -1)))
        }
        binding.toolBar.fabFilterNotes.setOnClickListener {
            val set = ConstraintSet()
            set.clone(binding.clNoteListRoot)
            set.setGuidelinePercent(binding.guidelineFilterHolder.id, 0.2f)
            TransitionManager.beginDelayedTransition(binding.clNoteListRoot, createTransition())
            binding.rvNotesList.apply {
                set.connect(id,
                    ConstraintSet.TOP,
                    binding.guidelineFilterHolder.id,
                    ConstraintSet.TOP,
                    0)
            }
            set.applyTo(binding.clNoteListRoot)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.noteListState.flowWithLifecycle(
                viewLifecycleOwner.lifecycle,
                Lifecycle.State.STARTED
            ).collectLatest {
                if (it.notes.isNotEmpty()) {
                    setRvData(it.notes)
                }
            }
        }
    }

    private fun createTransition(duration: Long = 1000L): Transition {
        val transition: Transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(0.5f)
        transition.duration = duration
        return transition
    }

    override fun layoutId(): Int = R.layout.fragment_note_list

    private fun initRecyclerView() {
        binding.adapter = noteListAdapter
        binding.rvNotesList.apply {
            addItemDecoration(
                NotesItemDecoration(
                    resources.getDimension(R.dimen.rv_padding).toInt()
                )
            )
        }
    }

    private fun setRvData(list: List<Note>) {
        noteListAdapter.updateData(list)
    }

    private fun initFilters() {
        val newRadioGroup = RadioGroup(context).apply {
            gravity = Gravity.CENTER
            orientation = LinearLayout.HORIZONTAL
        }
        NoteOrder::class.nestedClasses.forEach {
            newRadioGroup.addView(createFilterView(it.simpleName))
        }
        binding.glFiltersHolder.addView(newRadioGroup)

    }

    private fun createFilterView(noteOrder: String?): View {
        RadioButton(context).apply {
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
                    "CreatedDate" -> {
                        viewModel.onEvent(NoteListEvent.Order(NoteOrder.CreatedDate(OrderType.Ascending)))
                    }
                    "EditDate" -> {
                        viewModel.onEvent(NoteListEvent.Order(NoteOrder.EditDate(OrderType.Ascending)))
                    }
                    "Title" -> {
                        viewModel.onEvent(NoteListEvent.Order(NoteOrder.Title(OrderType.Ascending)))
                    }
                }
            }
            text = noteOrder
            maxLines = 1
            textSize = 16f
            return this
        }
    }

    private fun initSwipe() {
        val simpleCallback = (object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            private var isDeleting = false
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                viewModel.onEvent(NoteListEvent.DeleteNote((binding.rvNotesList.adapter as NoteListAdapter).getItem(
                    position)))
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean,
            ) {
                val paint = Paint()
                viewHolder.itemView.let {
                    if (dX > 0) {
                        val icon = BitmapFactory.decodeResource(
                            context?.resources, R.drawable.trash)
                        context?.let { context ->
                            paint.color = ContextCompat.getColor(context, R.color.note_red_dark)
                        }
                        c.drawRect(
                            it.left.toFloat(),
                            it.top.toFloat(),
                            dX,
                            it.bottom.toFloat(),
                            paint
                        )
                        c.drawBitmap(
                            icon,
                            it.left.toFloat() + resources.getDimension(R.dimen.icon_trash_padding)
                                .toInt(),
                            it.top.toFloat() + (it.bottom - it.top - icon.height) / 2,
                            paint
                        )
                    }
                    /* val swipeRatio: Float = 1f - Math.abs(dX) / viewHolder.itemView.width
                     if (swipeRatio <= 0.85 && !isDeleting) {
                         isDeleting = true
                         onSwiped(viewHolder, ItemTouchHelper.RIGHT)
                         Toast.makeText(requireContext(), "deleted", Toast.LENGTH_SHORT).show()
                     }*/
                }
                super.onChildDraw(c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive)
            }
        })
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvNotesList)
    }

}