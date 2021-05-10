package com.example.fashionecommercemobileapp.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.fashionecommercemobileapp.R
import kotlinx.android.synthetic.main.activity_account.*

class AccountActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        var username: String? = intent.getStringExtra("username")
        var idAccount: Int? = intent.getIntExtra("idAccount",0)

        /*Start ProfileActivity*/
        button_profile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java).apply {  }
            intent.putExtra("username",username)
            intent.putExtra("idAccount", idAccount)
            startActivity(intent)
        }
    }
}