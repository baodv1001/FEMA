package com.example.fashionecommercemobileapp.views


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fashionecommercemobileapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.activity_profile.*

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        handleNavigation()
    }
    private fun handleNavigation() {
        var navigationBar: BottomNavigationView = bnvMain

        navigationBar.selectedItemId = R.id.cart
        navigationBar.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_nvg -> startActivity(Intent(this@CartActivity, MainActivity::class.java))
                R.id.profile -> startActivity(Intent(this@CartActivity, AccountActivity::class.java))
                R.id.wish_list -> startActivity(Intent(this@CartActivity, WishListActivity::class.java))
            }
            this.finish()
            true
        })
    }
}