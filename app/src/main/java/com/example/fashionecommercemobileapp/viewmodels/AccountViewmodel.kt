package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.fashionecommercemobileapp.retrofit.repository.AccountRepository

class AccountViewmodel : ViewModel() {
    private  var accountRepository : AccountRepository? = null

    fun init()
    {
        accountRepository = AccountRepository()
    }

    fun ResultofSignup(username: String, pass: String, name: String, phoneNumber: String)
    {
        accountRepository!!.doSignup(username, pass, name, phoneNumber)
    }

    fun UpdatePW(username: String, pass: String, phoneNumber: String)
    {
        accountRepository!!.doUpdatePassword(username, pass ,phoneNumber)
    }

    fun CheckPW(username: String, phoneNumber: String) : LiveData<Boolean>?
    {
        accountRepository!!.doCheckAcc(username, phoneNumber)
        return accountRepository!!.getCheckPW()
    }
}