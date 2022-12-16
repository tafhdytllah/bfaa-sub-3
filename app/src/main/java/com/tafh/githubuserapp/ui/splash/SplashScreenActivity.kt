package com.tafh.githubuserapp.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.tafh.githubuserapp.R
import com.tafh.githubuserapp.ui.MainActivity
import com.tafh.githubuserapp.utils.Constants.countDownSplash

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            callNextActivity()
        }, countDownSplash)
    }

    private fun callNextActivity() {
        val intent = Intent(this@SplashScreenActivity, MainActivity:: class.java)
        startActivity(intent)
        finish()
    }
}