package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fashionecommercemobileapp.retrofit.repository.AccountRepository

class AccountViewModel : ViewModel() {
    private  var accountRepository : AccountRepository? = null

    fun init()
    {
        accountRepository = AccountRepository()
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