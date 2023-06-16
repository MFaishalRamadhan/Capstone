package com.bangkitacademy.capstoneproject.gudetamaaa.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkitacademy.capstoneproject.gudetamaaa.R

class SplashScreenFragment : Fragment() {

    lateinit var handler : Handler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginFragment = LoginFragment()

        handler = Handler()

        handler.postDelayed({
            val fragmentManager = parentFragmentManager
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.frame_container, loginFragment, LoginFragment::class.java.simpleName)
                commit()
            }
        }, 3000)
    }



}