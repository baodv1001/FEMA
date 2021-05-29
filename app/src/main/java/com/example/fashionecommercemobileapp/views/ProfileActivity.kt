package com.example.fashionecommercemobileapp.views

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.retrofit.repository.UserRepository
import com.example.fashionecommercemobileapp.model.User
import com.example.fashionecommercemobileapp.retrofit.utils.Status
import com.example.fashionecommercemobileapp.viewmodels.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*
import java.sql.Date
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Suppress("DEPRECATION")
class ProfileActivity : AppCompatActivity() {
    private var userViewModel: UserViewModel? = null
    private var listUser: List<User>? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        UserRepository.Companion.setContext(this@ProfileActivity)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel!!.init()

        var intent: Intent = intent
        val sp1 = getSharedPreferences("Login", Context.MODE_PRIVATE)
        val username = sp1.getString("Unm", null)
        val idAccount = sp1.getString("Id", null)
        Toast.makeText(this, idAccount.toString(), Toast.LENGTH_SHORT).show()
        userViewModel?.getUserData(idAccount.toString())?.observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        listUser = it?.data
                        text_username.text = username
                        text_name.text = listUser!![0].name
                        text_gender.text = listUser!![0].gender
                        text_birthday.text = listUser!![0].dateOfBirth?.toLocaleString()
                        text_user_email.text = listUser!![0].email
                        text_phone_number.text = listUser!![0].phoneNumber

                    }
                    Status.ERROR -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                    Status.LOADING -> {

                    }
                }
            }
        })
    }

    fun onClickBack(view: View) {
        super.onBackPressed()
    }

    fun onClickLogOut(view: View) {
        val sp = getSharedPreferences("Login", Context.MODE_PRIVATE)
        val Ed = sp.edit()
        Ed.putString("Unm", null)
        Ed.putString("Psw", null)
        Ed.putInt("Id", 0)
        Ed.commit()
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        this.startActivity(intent)

        if (this is Activity) {
            (this as Activity).finish()
        }

        Runtime.getRuntime().exit(0)
    }

}