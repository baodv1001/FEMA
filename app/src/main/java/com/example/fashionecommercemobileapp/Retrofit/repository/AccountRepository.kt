package com.example.fashionecommercemobileapp.retrofit.repository

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fashionecommercemobileapp.model.ShaPW
import com.example.fashionecommercemobileapp.retrofit.api.AccountApi
import com.example.fashionecommercemobileapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountRepository {
    private  var accountApi : AccountApi? = null
    var resultOfCheckPW : MutableLiveData<Boolean>? = MutableLiveData<Boolean>()

    fun getCheckPW() :  MutableLiveData<Boolean>?
    {
        return resultOfCheckPW
    }

    fun setResultofCheckPW(result:String)
    {
        resultOfCheckPW?.value = (result == "true")
    }

    companion object {
        private var accountRepository: AccountRepository? = null
        val instance: AccountRepository?
            get() {
                if (accountRepository == null) {
                    accountRepository = AccountRepository()
                }
                return accountRepository
            }
        private lateinit var context: Context
        fun setContext(con: Context) {
            context = con
        }
    }

    fun doSignUp(username: String, pass: String, name: String, phoneNumber: String)
    {
//        var passEncrypt : String = ShaPW.instance!!.doEncrypt(pass)
        var passEncrypt : String = ShaPW.instance!!.hash(pass)
        val call = accountApi!!.signUp(username, passEncrypt, name, phoneNumber)
        call.enqueue(object: Callback<String>
        {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result : String = response.body().toString()
                response.body()?.let {
                    if (result == "Success")
                    {
                        Toast.makeText(context, "Sign up success", Toast.LENGTH_SHORT).show()
                    } else  if (result == "FailedPhone"){
                        Toast.makeText(context, "Sign up failed. Your Phone is already taken", Toast.LENGTH_SHORT).show()
                    } else  if (result == "FailedUsername"){
                        Toast.makeText(context, "Sign up failed. Your Username is already taken", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun doUpdatePassword(username: String,pass: String, phoneNumber: String)
    {
        val call = accountApi!!.checkAccount(username, phoneNumber)
        call.enqueue(object : Callback<String>
        {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val result : String = response.body().toString()
                response.body()?.let {
                    if (result == "true")
                    {
                        setResultofCheckPW(result)
                        var passEncrypt = ShaPW.instance!!.doEncrypt(pass)
                        val callUpdate = accountApi!!.updatePassword(username, passEncrypt, phoneNumber)
                        callUpdate.enqueue(object : Callback<String>{
                            override fun onResponse(call: Call<String>, response: Response<String>) {
                                Toast.makeText(context, response.body().toString(), Toast.LENGTH_SHORT).show()
                            }

                            override fun onFailure(call: Call<String>, t: Throwable) {
                                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                            }
                        })
                    } else
                    {
                        Toast.makeText(context, "Username or number phone is not available", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun doCheckAcc(username: String, phoneNumber: String)
    {
        val call = accountApi!!.checkAccount(username, phoneNumber)
        call.enqueue(object : Callback<String>
        {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    setResultofCheckPW(it)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
    init {
        var retrofit: RetrofitClient = RetrofitClient()
        accountApi = retrofit.getRetrofitInstance()!!.create(AccountApi::class.java)
    }
}