package com.example.fashionecommercemobileapp.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fashionecommercemobileapp.R
import kotlinx.android.synthetic.main.activity_account.*

class AccountActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        val username: String? = intent.getStringExtra("username")
        val idAccount: Int = intent.getIntExtra("idAccount",0)

        /*Start ProfileActivity*/
        button_profile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java).apply {  }
            intent.putExtra("username",username)
            intent.putExtra("idAccount", idAccount)
            startActivity(intent)
        }
        button_address.setOnClickListener {
            val intent = Intent(this, AddressActivity::class.java).apply {  }
            intent.putExtra("idAccount",idAccount)
            startActivity(intent)
        }
        button_order.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java).apply {  }
            intent.putExtra("idAccount",idAccount)
            startActivity(intent)
        }
    }
}