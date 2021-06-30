package com.example.fashionecommercemobileapp.views

import android.app.AlertDialog
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
import java.util.*


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
        button_lang.setOnClickListener {
            showChangeLanguagesDialog()
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

    private fun showChangeLanguagesDialog() {
        val listLang = arrayOf("English", "Vietnamese")

        val mBuilder = AlertDialog.Builder(this@AccountActivity)
        mBuilder.setTitle(R.string.choose_lang)
        mBuilder.setSingleChoiceItems(listLang, -1) { dialog, which ->
            if (which == 0) {
                setLocate("en")
                recreate()
            } else if (which == 1) {
                setLocate("vi")
                recreate()
            }

            dialog.dismiss()
        }
        val mDialog = mBuilder.create()

        mDialog.show()
    }

    private fun setLocate(Lang: String) {

        val locale = Locale(Lang)

        Locale.setDefault(locale)

        val config = resources.configuration

        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
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
        Toast.makeText(this, R.string.click_back, Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}