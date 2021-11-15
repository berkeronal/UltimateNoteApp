package com.berker.ultimatenoteapp.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.berker.ultimatenoteapp.R

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                findNavController().navigate(R.id.action_splashFragment_to_noteListFragment)
            }
        }
        timer.start()
    }
}