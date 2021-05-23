package com.example.fashionecommercemobileapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fashionecommercemobileapp.R
import com.example.fashionecommercemobileapp.retrofit.utils.Status
import com.example.fashionecommercemobileapp.model.Account
import com.example.fashionecommercemobileapp.retrofit.repository.AccountRepository
import com.example.fashionecommercemobileapp.viewmodels.AccountViewModel
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
            accountViewModel?.doLogin(username, password)?.observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            listAccount = it?.data
                            if (listAccount?.isEmpty() == false) {
                                val intent = Intent(this, AccountActivity::class.java).apply { }
                                intent.putExtra("username", username)
                                intent.putExtra("idAccount",listAccount?.first()?.id)
                                startActivity(intent)
                            }
                            else
                                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                        Status.ERROR -> {
                            Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                        }
                        Status.LOADING -> {

                        }
                    }
                }
            })
        }
    }
    fun onClickSignUp(view: View) {
        val intent = Intent(this, SignUpActivity::class.java).apply {  }
        startActivity(intent)
    }
}