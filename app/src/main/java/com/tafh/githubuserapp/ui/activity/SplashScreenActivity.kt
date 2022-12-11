package com.tafh.githubuserapp.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.tafh.githubuserapp.R

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            callNextActivity()
        }, 3000)
    }

    private fun callNextActivity() {
        val intent = Intent(this@SplashScreenActivity, MainActivity:: class.java)
        startActivity(intent)
        finish()
    }
}