package com.berker.ultimatenoteapp.ui.note.detail

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.setMargins
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.berker.ultimatenoteapp.R
import com.berker.ultimatenoteapp.databinding.FragmentNoteDetailBinding
import com.berker.ultimatenoteapp.domain.model.NoteColor
import com.berker.ultimatenoteapp.ui.note.base.BaseFragment
import com.berker.ultimatenoteapp.ui.note.detail.util.UiEvent
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NoteDetailFragment : BaseFragment<FragmentNoteDetailBinding>() {

    private val viewModel: NoteDetailViewModel by viewModels()

    override fun initUi() {
        binding.etvTitle.addTextChangedListener(
            afterTextChanged = { newText ->
                viewModel.onEvent(NoteDetailEvent.WroteTitle(newText.toString()))
            }
        )
        binding.etvImageUrl.addTextChangedListener(
            afterTextChanged = { newText ->
                viewModel.onEvent(NoteDetailEvent.WroteImageUrl(newText.toString()))
            }
        )
        binding.etvDescription.addTextChangedListener(
            afterTextChanged = { newText ->
                viewModel.onEvent(NoteDetailEvent.WroteDescription(newText.toString()))
            }
        )
        lifecycleScope.launchWhenCreated {
            binding.apply {
                initColorPickers()
                initFabSaveButton()
                viewModel.noteDetailState.collectLatest {
                    etvTitle.setTextKeepState(it.title)
                    etvDescription.setTextKeepState(it.description)
                    etvImageUrl.setTextKeepState(it.imageUrl)
                    bgHolder.setBackgroundColor(getColor(it.color))
                    if (it.imageUrl != "") setImage(it.imageUrl)
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.uiEventFlow.collectLatest { uiEvent ->
                when (uiEvent) {
                    UiEvent.SaveNote -> findNavController().navigateUp()
                    is UiEvent.ShowErrorDialog -> showErrorDialog(uiEvent.message)
                }
            }
        }
    }

    override fun layoutId(): Int = R.layout.fragment_note_detail

    private fun setImage(url: String) {
        binding.apply {
            Glide.with(root.context)
                .load(url)
                .into(ivNoteImage)
        }
    }

    private fun initColorPickers() {
        NoteColor.values().forEach {
            val layout = createFrameLayout()
            val fab = createFloatingActionButton(it.value)
            layout.addView(fab)
            binding.llColorsHolder.addView(layout)
        }
    }

    private fun initFabSaveButton() {
        binding.apply {
            fbaSaveNote.setOnClickListener {
                viewModel.onEvent(NoteDetailEvent.SaveNote)
            }
        }
    }

    private fun createFrameLayout(): FrameLayout {
        return FrameLayout(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT
            ).apply {
                setMargins(12)
                weight = 0.5f
                gravity = Gravity.CENTER
            }
        }
    }

    private fun getBackgroundColor(): Int {
        return try {
            val layout = binding.clNoteDetailRoot
            val viewColor = layout.background as ColorDrawable
            viewColor.color
        } catch (e: Exception) {
            showErrorDialog("Error While Getting background color")
            -1
        }
    }

    private fun createFloatingActionButton(colorId: Int): FloatingActionButton {
        return FloatingActionButton(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            ).apply {
                setMargins(12)
                backgroundTintList = ColorStateList.valueOf(getColor(colorId))
                setOnClickListener {
                    floatButtonClickActions(colorId)
                }
            }
        }
    }

    private fun floatButtonClickActions(colorId: Int) {
        val animation = ObjectAnimator.ofInt(binding.bgHolder,
            "backgroundColor",
            getBackgroundColor(),
            getColor(colorId)).apply {
            interpolator = LinearInterpolator()
            duration = 500
            setEvaluator(ArgbEvaluator())
        }
        val animationSet = AnimatorSet()
        animationSet.play(animation)
        animationSet.start()
        changeInputsBackground(colorId)
        viewModel.onEvent(NoteDetailEvent.ChangeColor(colorId))
    }

    private fun changeInputsBackground(colorId: Int) {
        val animation = ObjectAnimator.ofInt(binding.bgHolder,
            "backgroundColor",
            getBackgroundColor(),
            getColor(colorId)).apply {
            interpolator = LinearInterpolator()
            duration = 500
            setEvaluator(ArgbEvaluator())
        }
        val animationSet = AnimatorSet()
        animationSet.play(animation)
        animationSet.start()
    }

    private fun getColor(colorId: Int): Int {
        return context?.let {
            ContextCompat.getColor(it, colorId)
        } ?: -1
    }
}