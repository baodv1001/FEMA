package com.example.fashionecommercemobileapp.Retrofit.api

import com.example.fashionecommercemobileapp.model.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserApi {
    @FormUrlEncoded
    @POST("get_user.php")
    fun getUser(@Field("idAccount") idAccount: String): Call<List<User>>
}