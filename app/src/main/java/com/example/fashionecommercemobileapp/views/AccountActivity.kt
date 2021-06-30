package com.example.fashionecommercemobileapp.views

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.fashionecommercemobileapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.activity_profile.*


class AccountActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        val username: String? = intent.getStringExtra("username")

        val sp1 = getSharedPreferences("Login", Context.MODE_PRIVATE)
        val idAccount = sp1.getString("Id", null)
        /*Start ProfileActivity*/
        button_profile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java).apply { }
            startActivity(intent)
        }
        button_address.setOnClickListener {
            val intent = Intent(this, AddressActivity::class.java).apply { }
            startActivity(intent)
        }
        button_order.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java).apply { }
            startActivity(intent)
        }
        handleNavigation()
    }

    private fun handleNavigation() {
        var navigationBar: BottomNavigationView = bnvMain

        navigationBar.selectedItemId = R.id.profile
        navigationBar.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_nvg -> startActivity(Intent(this, MainActivity::class.java))
                R.id.cart -> startActivity(Intent(this, CartActivity::class.java))
                R.id.wish_list -> startActivity(Intent(this, WishListActivity::class.java))
            }
            this.finish()
            true
        })
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            startActivity(intent)
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}