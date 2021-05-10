package com.example.fashionecommercemobileapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.get
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.Retrofit.repository.AccountRepository
import com.example.fashionecommercemobileapp.Retrofit.repository.UserRepository
import com.example.fashionecommercemobileapp.model.Account
import com.example.fashionecommercemobileapp.model.User
import com.example.fashionecommercemobileapp.viewmodels.AccountViewModel
import com.example.fashionecommercemobileapp.viewmodels.UserViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*


class LoginActivity : AppCompatActivity() {
    private var accountViewModel: AccountViewModel? = null
    private var listAccount: List<Account>? = null
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        AccountRepository.Companion.setContext(this@LoginActivity)
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel::class.java)
        accountViewModel!!.init()

        /*Login*/

        btnLogin.setOnClickListener {
            val username = tfUsername.editText?.text.toString()
            val password = tfPassword.editText?.text.toString()
            listAccount = accountViewModel!!.getAccountData(username, password)?.value
            if (listAccount?.isEmpty() == false) {
                val intent = Intent(this, AccountActivity::class.java).apply {  }
                intent.putExtra("idAccount", listAccount?.first()?.idAccount)
                intent.putExtra("username",username)
                startActivity(intent)


            }
            else {
                Toast.makeText(baseContext,"Login Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }
}