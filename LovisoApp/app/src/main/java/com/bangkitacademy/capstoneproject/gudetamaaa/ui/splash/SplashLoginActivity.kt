package com.bangkitacademy.capstoneproject.gudetamaaa.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bangkitacademy.capstoneproject.gudetamaaa.R
import com.bangkitacademy.capstoneproject.gudetamaaa.databinding.ActivitySplashLoginBinding

class SplashLoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        val splashFragment = SplashScreenFragment()
        val fragment = fragmentManager.findFragmentByTag(SplashScreenFragment::class.java.simpleName)

        if(fragment !is SplashScreenFragment){
            Log.d("MyFragment", "Fragment Name : " + SplashScreenFragment::class.java.simpleName)
            fragmentManager
                .beginTransaction()
                .add(R.id.frame_container, splashFragment, SplashScreenFragment::class.java.simpleName)
                .commit()
        }
    }
}