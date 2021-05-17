package com.example.fashionecommercemobileapp.retrofit.repository

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fashionecommercemobileapp.retrofit.api.UserApi
import com.example.fashionecommercemobileapp.model.User
import com.example.fashionecommercemobileapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    private var listUsers: MutableLiveData<List<User>>? = MutableLiveData<List<User>>()
    private var userApi: UserApi = RetrofitClient().userApi

    fun setUserData(userData: List<User>) {
        listUsers?.value = userData
    }
    fun getUserData(): MutableLiveData<List<User>>? {
        return listUsers
    }

    companion object {
        private var userRepository: UserRepository? = null
        val instance: UserRepository?
            get() {
                if (userRepository == null) {
                    userRepository = UserRepository()
                }
                return  userRepository
            }
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context
        fun setContext(con: Context) {
            context = con
        }
    }
    suspend fun doUserRequest(idAccount: String) = userApi.getUser(idAccount)

    /*init {
        val retrofit = RetrofitClient()
        userApi = retrofit.getRetrofitInstance()!!.create(UserApi::class.java)
    }*/
}