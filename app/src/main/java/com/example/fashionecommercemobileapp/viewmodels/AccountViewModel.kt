package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fashionecommercemobileapp.Retrofit.repository.AccountRepository
import com.example.fashionecommercemobileapp.model.Account

class AccountViewModel: ViewModel() {
    private var accountData: MutableLiveData<List<Account>>? = null
    private var accountRepository: AccountRepository? = null
    
    fun init()
    {
        accountRepository = AccountRepository()
    }
    fun getAccountData(username: String, password: String ): LiveData<List<Account>>? {
        accountRepository!!.doLogin(username, password)
        accountData = accountRepository?.getAccountData()
        return accountData
    }

    fun resultOfSignUp(username: String, pass: String, name: String, phoneNumber: String)
    {
        accountRepository!!.doSignUp(username, pass, name, phoneNumber)
    }

    fun updatePW(username: String, pass: String, phoneNumber: String)
    {
        accountRepository!!.doUpdatePassword(username, pass ,phoneNumber)
    }

    fun checkPW(username: String, phoneNumber: String) : LiveData<Boolean>?
    {
        accountRepository!!.doCheckAcc(username, phoneNumber)
        return accountRepository!!.getCheckPW()
    }
}