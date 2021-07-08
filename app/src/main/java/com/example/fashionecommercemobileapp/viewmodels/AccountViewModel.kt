package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.fashionecommercemobileapp.retrofit.repository.AccountRepository
import com.example.fashionecommercemobileapp.model.Account
import com.example.fashionecommercemobileapp.model.ShaPW
import com.example.fashionecommercemobileapp.retrofit.utils.Resource
import kotlinx.coroutines.Dispatchers

class AccountViewModel() : ViewModel() {
    private var accountData: MutableLiveData<List<Account>>? = null
    private var accountRepository: AccountRepository? = null
    fun init()
    {
        if(accountData != null) {
            return
        }
        accountRepository = AccountRepository()
    }
    /*
    fun getAccountData(username: String, password: String ): LiveData<List<Account>>? {
        accountRepository!!.doLogin(username, password)
        accountData = accountRepository?.getAccountData()
        return accountData
    }*/


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
    fun doLogin(username: String, password: String) = liveData(Dispatchers.IO) {
        val passEncrypt: String = ShaPW.instance!!.hash(password)
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = accountRepository!!.doLogin(username, passEncrypt)))
        }
        catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!" ))
        }
    }
    fun doChangePassword(idAccount: Int, password: String) {
        accountRepository!!.doChangePassWordRequest(idAccount, password)
    }
}