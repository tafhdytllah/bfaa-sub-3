package com.tafh.githubuserapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tafh.githubuserapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
    }
}