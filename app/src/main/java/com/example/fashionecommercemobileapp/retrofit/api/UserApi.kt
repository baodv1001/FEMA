package com.example.fashionecommercemobileapp.retrofit.api

import com.example.fashionecommercemobileapp.model.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserApi {
    @FormUrlEncoded
    @POST("get_user.php")
    suspend fun getUser(@Field("idAccount") idAccount: String): List<User>
}