package com.example.fashionecommercemobileapp.views

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.model.Account
import com.example.fashionecommercemobileapp.model.User
import com.example.fashionecommercemobileapp.retrofit.repository.AccountRepository
import com.example.fashionecommercemobileapp.retrofit.repository.UserRepository
import com.example.fashionecommercemobileapp.viewmodels.AccountViewModel
import com.example.fashionecommercemobileapp.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : AppCompatActivity() {

    private var userViewModel: UserViewModel? = null
    private var accountViewModel: AccountViewModel? = null
    private var listAccount: List<Account>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        UserRepository.Companion.setContext(this@ChangePasswordActivity)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel!!.init()

        AccountRepository.Companion.setContext(this@ChangePasswordActivity)
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel::class.java)
        accountViewModel!!.init()

        val sp = getSharedPreferences("Login",Context.MODE_PRIVATE)
        val idAccount : Int = sp.getString("Id",null)?.toInt()!!
        val password = sp.getString("Psw",null)
        val username = sp.getString("Unm", null)

        button_change.setOnClickListener {
            var checkPhone : Boolean = true
            var checkEmail : Boolean = true
            userViewModel?.checkPhoneNumber(text_input_Phone_Number.text.toString())?.observe(this,Observer {
                if (it == false) {
                    checkPhone = false
                    Toast.makeText(this, "Phone number is invalid", Toast.LENGTH_SHORT).show()
                }
            })
            userViewModel?.checkEmail(text_input_Phone_Number.text.toString())?.observe(this, Observer {
                if (it == false) {
                    checkEmail = false
                    Toast.makeText(this, "Email is invalid", Toast.LENGTH_SHORT).show()
                }
            })

            if (checkTextField(password!!) && (checkPhone || checkEmail)) {
                changePassword(idAccount)
                val ed = sp.edit()
                ed.putString("Psw",text_input_new_password.text.toString())
                onClickLogOut(button_change)
            }
        }
    }

    fun onClickBack(view: View) {
        super.onBackPressed()
    }

    fun checkTextField(password: String): Boolean {
        if(text_input_Password.text.toString().isEmpty() ||
                text_input_Phone_Number.text.toString().isEmpty() ||
                text_input_new_password.text.toString().isEmpty() ||
                text_input_re_password.text.toString().isEmpty()) {
            Toast.makeText(this, "Please filled text field", Toast.LENGTH_SHORT).show()
            return false
        } else if (password != text_input_Password.text.toString()) {
            Toast.makeText(this, "Password is wrong", Toast.LENGTH_SHORT).show()
            return false
        } else if (text_input_new_password.text.toString() != text_input_re_password.text.toString()) {
            Toast.makeText(this, "New password and retype not match", Toast.LENGTH_SHORT).show()
            return false
        } else{
            return true
        }
    }
    fun changePassword(idAccount: Int) {
        val newPass = text_input_new_password.text.toString()
        accountViewModel?.doChangePassword(idAccount, newPass)
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