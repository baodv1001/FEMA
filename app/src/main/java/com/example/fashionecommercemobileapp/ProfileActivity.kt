package com.example.fashionecommercemobileapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)
        val backButton : Button = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            super.onBackPressed()
        }
    }
}