package com.bangkitacademy.capstoneproject.loviso.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.bangkitacademy.capstoneproject.loviso.R
import com.bangkitacademy.capstoneproject.loviso.ui.account.LoginActivity

class SplashActivity : AppCompatActivity() {

    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        handler = Handler()

        handler.postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}