package com.example.fashionecommercemobileapp.views

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.service.AppService
import kotlinx.android.synthetic.main.activity_on_boarding.*

class OnBoardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        val mIntent = Intent(this, AppService::class.java)
        startService(mIntent)
        btnStartNow.setOnClickListener()
        {
            val intent = Intent(this, MainActivity::class.java).apply { }
            val sp = getSharedPreferences("Login", Context.MODE_PRIVATE)
            val Ed = sp.edit()
            Ed.putString("Id", "0")
            Ed.commit()
            startActivity(intent)
            this.finish()
        }
        btnLoginNow.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java).apply { }
            startActivity(intent)
            this.finish()
        }
    }
}