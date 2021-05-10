package com.example.fashionecommercemobileapp.Retrofit.repository

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fashionecommercemobileapp.Retrofit.api.AccountApi
import retrofit2.Call
import com.example.fashionecommercemobileapp.model.Account
import com.example.fashionecommercemobileapp.model.ShaPW
import com.example.fashionecommercemobileapp.retrofit.RetrofitClient
import retrofit2.Callback
import retrofit2.Response

class AccountRepository {
    private var listAccounts: MutableLiveData<List<Account>>? = MutableLiveData<List<Account>>()
    private var accountApi: AccountApi? = null
    /*var resultOfCheckLogin: MutableLiveData<Boolean>? = MutableLiveData<Boolean>()*/

    fun setAccountData(accountData: List<Account>){
        listAccounts?.value = accountData
    }
    fun getAccountData(): MutableLiveData<List<Account>>? {
        return listAccounts
    }
    /*fun getResultOfLogin(): MutableLiveData<Boolean>? {
        return  resultOfCheckLogin
    }
    fun setResultOfLogin(result: String) {
        resultOfCheckLogin?.value = (result == "true")
    }*/
    companion object {
        private var accountRepository : AccountRepository? = null
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

    fun doLogin(username: String, password: String) {
        val passEncrypt: String = ShaPW.instance!!.hash(password)
        val callAccount:Call<List<Account>> = accountApi!!.getAccount(username, passEncrypt)
        callAccount.enqueue(object: Callback<List<Account>> {

            override fun onResponse(call: Call<List<Account>>, response: Response<List<Account>>) {
                response.body()?.let { setAccountData(it) }
            }

            override fun onFailure(call: Call<List<Account>>, t: Throwable) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    init {
        val retrofit = RetrofitClient()
        accountApi = retrofit.getRetrofitInstance()!!.create(AccountApi::class.java)
    }
}