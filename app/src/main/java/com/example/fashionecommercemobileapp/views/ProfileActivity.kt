package com.example.fashionecommercemobileapp.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.Retrofit.repository.UserRepository
import com.example.fashionecommercemobileapp.model.User
import com.example.fashionecommercemobileapp.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity(){
    private var userViewModel: UserViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        UserRepository.Companion.setContext(this@ProfileActivity)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel!!.init()

        var intent: Intent = intent
        var idAccount: Int? = intent.getIntExtra("idAccount", 0)
        var username: String? = intent.getStringExtra("username")

        val listUser: List<User>? = userViewModel!!.getUserData(idAccount.toString())?.value
        text_username.text = username
        text_name.text = listUser!![0].name
        text_gender.text = listUser[0].gender
        text_birthday.text = listUser[0].dateOfBirth.toString()
        text_phone_number.text = listUser[0].phoneNumber
    }

    fun onClickBack(view: View) {
        super.onBackPressed()
    }
}