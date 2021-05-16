package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fashionecommercemobileapp.retrofit.repository.UserRepository
import com.example.fashionecommercemobileapp.model.User

class UserViewModel: ViewModel() {
    private var userData: MutableLiveData<List<User>>? = null
    private var userRepository: UserRepository? = null

    public fun init() {
        if(userData != null)
            return
        userRepository = UserRepository()
    }
    fun getUserData(idAccount: String): LiveData<List<User>>? {
        userRepository!!.doUserRequest(idAccount)
        userData = userRepository?.getUserData()
        return userData
    }
}