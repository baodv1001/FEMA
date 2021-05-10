package com.example.fashionecommercemobileapp.Retrofit.repository

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fashionecommercemobileapp.Retrofit.api.UserApi
import com.example.fashionecommercemobileapp.model.User
import com.example.fashionecommercemobileapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    private var listUsers: MutableLiveData<List<User>>? = MutableLiveData<List<User>>()
    private var userApi: UserApi? = null

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
    fun doUserRequest(idAccount: String) {
        val callUser: Call<List<User>> = userApi!!.getUser(idAccount)
        callUser.enqueue(object: Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                response.body()?.let { setUserData(it) }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    init {
        val retrofit = RetrofitClient()
        userApi = retrofit.getRetrofitInstance()!!.create(UserApi::class.java)
    }
}