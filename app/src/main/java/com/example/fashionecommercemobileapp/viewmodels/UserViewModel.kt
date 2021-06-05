package com.example.fashionecommercemobileapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.fashionecommercemobileapp.retrofit.repository.UserRepository
import com.example.fashionecommercemobileapp.model.User
import com.example.fashionecommercemobileapp.retrofit.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.util.*

class UserViewModel: ViewModel() {
    private var userData: MutableLiveData<List<User>>? = null
    private var userRepository: UserRepository? = null

    fun init() {
        if(userData != null)
            return
        userRepository = UserRepository()
    }
    fun getUserData(idAccount: String) = liveData(Dispatchers.IO) {
       emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = userRepository!!.doUserRequest(idAccount)))
        }
        catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun updateUserDate(idAccount: Int, name: String, gender: String, dateOfBirth: String) {
        userRepository?.doUpdateUserRequest(idAccount, name, gender, dateOfBirth)
    }
}