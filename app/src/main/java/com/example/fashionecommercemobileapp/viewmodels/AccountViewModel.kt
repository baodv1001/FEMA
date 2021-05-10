package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fashionecommercemobileapp.Retrofit.repository.AccountRepository
import com.example.fashionecommercemobileapp.model.Account

class AccountViewModel: ViewModel() {
    private var accountData: MutableLiveData<List<Account>>? = null
    private var accountRepository: AccountRepository? = null

    fun init() {
        if(accountData != null) {
            return
        }
        accountRepository = AccountRepository()
    }

    fun getAccountData(username: String, password: String ): LiveData<List<Account>>? {
        accountRepository!!.doLogin(username, password)
        accountData = accountRepository?.getAccountData()
        return accountData
    }
}