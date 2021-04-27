package com.example.fashionecommercemobileapp

import  android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account)
        val profileButton: Button = findViewById(R.id.profile_button)
        clickButton(profileButton)
        val orderButton : Button = findViewById(R.id.order_button)
        orderButton.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java).apply {  }
            startActivity(intent)
        }
        val addressButton : Button = findViewById(R.id.address_button)
        addressButton.setOnClickListener {
            val intent = Intent(this, AddressActivity::class.java).apply{}
            startActivity(intent)
        }

    }
    private fun clickButton (button: Button) {

        button.setOnClickListener {
            val intent = Intent (this, ProfileActivity::class.java).apply{}
            startActivity(intent)
        }
    }
}