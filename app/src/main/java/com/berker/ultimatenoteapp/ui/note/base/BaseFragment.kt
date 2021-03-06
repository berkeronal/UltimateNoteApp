package com.berker.ultimatenoteapp.ui.note.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.berker.ultimatenoteapp.R

// _________________________________
//|  _____________________________  |
//| |       ❚❚█══BERKER══█❚❚       | |
//| |    Created by Berker ÖNAL   | |
//| |     berkeronal@gmail.com    | |
//| |         13.11.2021          | |
//| |_____________________________| |
//|_________________________________|
abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    private var _binding: T? = null
    protected val binding get() = _binding!!
    abstract fun layoutId(): Int
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        initUi()

    }

    protected fun Fragment.showErrorDialog(errorMessage: String) {
        MaterialDialog(requireContext()).show {
            cancelable(true)
            cancelOnTouchOutside(true)
            title(text = getString(R.string.popup_title))
            message(text = "Error while loading content, try again later. $errorMessage")
            positiveButton(text = "Got it!")
        }
    }

    protected fun Fragment.showDialog(title: String, message: String) {
        MaterialDialog(requireContext()).show {
            cancelable(true)
            cancelOnTouchOutside(true)
            title(text = title)
            message(text = message)
            positiveButton(text = "Got it!")
        }
    }

    open fun initUi() {}
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}